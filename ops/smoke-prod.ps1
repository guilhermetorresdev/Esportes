param(
    [string]$BaseUrl = $(if ($env:PORTAL_BASE_URL) { $env:PORTAL_BASE_URL } else { 'http://localhost:8081' }),
    [string]$Username = $env:PORTAL_SMOKE_USERNAME,
    [string]$Password = $env:PORTAL_SMOKE_PASSWORD
)

$ErrorActionPreference = 'Stop'
$BaseUrl = $BaseUrl.TrimEnd('/')
$session = New-Object Microsoft.PowerShell.Commands.WebRequestSession

function Request($Path, $Method = 'Get', $Body = $null, $NoRedirect = $false) {
    $params = @{ Uri = "$BaseUrl$Path"; Method = $Method; WebSession = $session; UseBasicParsing = $true; ErrorAction = 'Stop' }
    if ($Body) { $params.Body = $Body }
    if ($NoRedirect) { $params.MaximumRedirection = 0 }
    Invoke-WebRequest @params
}

$home = Request '/'
if ($home.StatusCode -ne 200) { throw "Home retornou HTTP $($home.StatusCode)." }
$csrf = Request '/api/auth/csrf'
if ($csrf.StatusCode -ne 200 -or -not $session.Cookies.GetCookies($BaseUrl)['XSRF-TOKEN']) { throw 'Endpoint CSRF não publicou token/cookie.' }
$legacy = Request '/futsal.html' 'Get' $null $true
if ($legacy.StatusCode -ne 301 -or $legacy.Headers.Location -notmatch '^/futsal(?:\?|$)') { throw "Rota legada /futsal.html não redirecionou corretamente." }

if ($Username -and $Password) {
    $login = Request '/login' 'Post' @{ username = $Username; password = $Password }
    if ($login.StatusCode -ne 200) { throw "Login de smoke retornou HTTP $($login.StatusCode)." }
    $me = Request '/api/auth/me'
    if ($me.StatusCode -ne 200) { throw "Sessão não foi reconhecida: HTTP $($me.StatusCode)." }
    $logout = Request '/logout' 'Post'
    if ($logout.StatusCode -notin @(200, 204)) { throw "Logout retornou HTTP $($logout.StatusCode)." }
}

Write-Output "Smoke test aprovado em $BaseUrl"
