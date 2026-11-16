param(
    [string]$DataDir = $(if ($env:PORTAL_DATA_DIR) { $env:PORTAL_DATA_DIR } else { './backend/data' }),
    [string]$VideosDir = $(if ($env:PORTAL_VIDEOS_DIR) { $env:PORTAL_VIDEOS_DIR } else { './frontend/public/Videos' }),
    [string]$ImagesDir = $(if ($env:PORTAL_IMAGES_DIR) { $env:PORTAL_IMAGES_DIR } else { './frontend/public/Imagens' }),
    [string]$OutputDir = $(if ($env:PORTAL_BACKUP_DIR) { $env:PORTAL_BACKUP_DIR } else { './backups' })
)

$ErrorActionPreference = 'Stop'
$stamp = Get-Date -Format 'yyyyMMdd-HHmmss'
New-Item -ItemType Directory -Path $OutputDir -Force | Out-Null
$destination = Join-Path (Resolve-Path $OutputDir) "portal-$stamp"
New-Item -ItemType Directory -Path $destination -Force | Out-Null

foreach ($source in @($DataDir, $VideosDir, $ImagesDir)) {
    if (-not (Test-Path -LiteralPath $source -PathType Container)) {
        throw "Diretório não encontrado: $source"
    }
}

foreach ($item in @(
    @{ Source = $DataDir; Target = (Join-Path $destination 'data') },
    @{ Source = $VideosDir; Target = (Join-Path $destination 'Videos') },
    @{ Source = $ImagesDir; Target = (Join-Path $destination 'Imagens') }
)) {
    New-Item -ItemType Directory -Path $item.Target -Force | Out-Null
    Get-ChildItem -LiteralPath $item.Source -Force | Copy-Item -Destination $item.Target -Recurse -Force
}
Get-ChildItem -LiteralPath $destination -Recurse -File | Get-FileHash -Algorithm SHA256 |
    ConvertTo-Json | Set-Content -LiteralPath (Join-Path $destination 'SHA256SUMS.json') -Encoding utf8

Write-Output "Backup criado em $destination"
