# Banco MySQL de núcleos

Este banco é uma base auxiliar, independente do portal. Ele foi criado para
guardar o cadastro de núcleos e servir de fonte para uma futura integração,
sem alterar o backend, a autenticação ou as rotas atuais.

## Carga inicial

Execute `nucleos_mysql.sql` em uma instância MySQL com um usuário autorizado a
criar banco de dados:

```bash
mysql -u SEU_USUARIO -p < database/nucleos_mysql.sql
```

O script cria o banco `portal_esporte_nucleos` e quatro tabelas:

- `regionais`: aba `Regional`;
- `nucleos`: abas `nucleo`, `codigo_nucleo` e `nucleo_matriculados` consolidadas;
- `modalidades`: aba `modalidade`;
- `nucleos_modalidades`: aba `nucle_modalidade` com a quantidade de matriculados por oferta.

Os comandos de carga são idempotentes: executar o arquivo novamente atualiza
os registros pelos códigos, sem criar duplicatas.

## Validação esperada

Após executar o script, as tabelas devem conter 11 regionais, 30 núcleos,
25 modalidades e 95 relações núcleo–modalidade.

## Limite atual

Não há conexão automática com o portal. A criação de telas, API, permissões,
rotinas de manutenção e sincronização ficam para uma etapa posterior, quando
o fluxo de cadastro for definido.
