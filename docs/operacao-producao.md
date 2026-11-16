# Operação em produção

## Configuração

Inicie o backend com `SPRING_PROFILES_ACTIVE=prod`. O perfil de produção desabilita o console H2, SQL detalhado e alterações automáticas do esquema (`ddl-auto=validate`). Credenciais e caminhos devem vir do ambiente; não coloque senhas em arquivos versionados.

O compose de produção é `docker-compose.prod.yml`. Antes de iniciar, defina pelo menos `SPRING_DATASOURCE_USERNAME` e `SPRING_DATASOURCE_PASSWORD`. O padrão continua sendo H2 em arquivo para manter a implantação atual; uma migração para um banco servidor deve fornecer `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_DRIVER` e a dependência JDBC correspondente.

## Evolução do esquema

Faça backup antes de cada mudança. Em produção, gere e revise uma migração SQL versionada em uma janela controlada, aplique-a primeiro em ambiente descartável e só então mantenha `ddl-auto=validate` para detectar divergências sem alterar dados automaticamente.

## Backup e restauração

O backup precisa incluir o diretório do banco (`backend/data` ou volume `/app/data`), vídeos e imagens. Execute `./ops/backup-portal.ps1` com o serviço parado ou em uma janela consistente. O script gera uma pasta datada e `SHA256SUMS.json`.

Para restaurar em ambiente descartável, execute `./ops/restore-portal.ps1 -BackupDir <pasta-do-backup>`, confirme `RESTAURAR`, inicie o serviço e valide login, leitura do banco, listagem de vídeos e imagens e uma operação administrativa de teste. Em produção, faça a restauração somente com janela aprovada e mantenha o backup anterior preservado.
