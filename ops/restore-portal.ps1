param(
    [Parameter(Mandatory = $true)][string]$BackupDir,
    [string]$DataDir = $(if ($env:PORTAL_DATA_DIR) { $env:PORTAL_DATA_DIR } else { './backend/data' }),
    [string]$VideosDir = $(if ($env:PORTAL_VIDEOS_DIR) { $env:PORTAL_VIDEOS_DIR } else { './frontend/public/Videos' }),
    [string]$ImagesDir = $(if ($env:PORTAL_IMAGES_DIR) { $env:PORTAL_IMAGES_DIR } else { './frontend/public/Imagens' }),
    [switch]$Force
)

$ErrorActionPreference = 'Stop'
if (-not (Test-Path -LiteralPath (Join-Path $BackupDir 'SHA256SUMS.json'))) {
    throw 'Backup inválido: SHA256SUMS.json não encontrado.'
}
if (-not $Force) {
    $confirm = Read-Host "Isso substituirá banco e uploads nos diretórios configurados. Digite RESTAURAR para continuar"
    if ($confirm -ne 'RESTAURAR') { throw 'Restauração cancelada.' }
}

foreach ($pair in @(
    @{ Source = 'data'; Target = $DataDir },
    @{ Source = 'Videos'; Target = $VideosDir },
    @{ Source = 'Imagens'; Target = $ImagesDir }
)) {
    New-Item -ItemType Directory -Path $pair.Target -Force | Out-Null
    Get-ChildItem -LiteralPath (Join-Path $BackupDir $pair.Source) -Force | Copy-Item -Destination $pair.Target -Recurse -Force
}

Write-Output 'Restauração concluída. Valide o serviço e os arquivos antes de liberar o tráfego.'
