param([string]$BaseUrl = $(if ($env:PORTAL_FRONTEND_URL) { $env:PORTAL_FRONTEND_URL } else { 'http://localhost:5173' }))
$ErrorActionPreference = 'Stop'; $BaseUrl = $BaseUrl.TrimEnd('/')
$rotas = @('/', '/entrar', '/agenda', '/inscricao?modalidade=futsal', '/almoxarifado', '/agendamento-quadras', '/comunicacao', '/esporte-nas-regionais', '/detalhes-evento?id=1')
foreach ($rota in $rotas) { $resposta = Invoke-WebRequest -Uri "$BaseUrl$rota" -UseBasicParsing -MaximumRedirection 0; if ($resposta.StatusCode -ne 200) { throw "$rota retornou HTTP $($resposta.StatusCode)." } }
foreach ($legada in @('/login.html', '/agenda.html', '/inscricao.html', '/almoxarifado.html', '/agendamento-quadras.html', '/comunicacao.html')) { try { $resposta = Invoke-WebRequest -Uri "$BaseUrl$legada" -UseBasicParsing -MaximumRedirection 0 } catch { $resposta = $_.Exception.Response }; if ($resposta.StatusCode -notin @(200, 301, 302)) { throw "$legada retornou HTTP $([int]$resposta.StatusCode)." } }
Write-Output "Smoke de rotas React aprovado em $BaseUrl"
