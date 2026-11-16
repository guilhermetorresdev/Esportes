# Registro de retomada — comando :return

## Release v1.8 — preparação — 16/11/2026

- A versão acumulada do portal foi validada antes do versionamento.
- Frontend: `npm run test:accessibility` aprovado em 12/12 e `npm run build` aprovado.
- Backend: `backend/mvnw.cmd test` aprovado em 87/87.
- A consulta de tags remotas falhou por indisponibilidade de conexão com o GitHub; a escolha de `v1.8` foi conferida contra as tags locais, que chegam até `v1.5`.
- Próximo passo: criar o commit local `release: v1.8 — portal adaptado e fluxos administrativos`, tag anotada `v1.8` e enviar ambos ao `origin`, se a rede estiver disponível.

## Adaptação Cloudflare — lote 1 — 16/11/2026

- Reconhecimento do frontend concluído para preparação de Workers Static Assets.
- O build atual continua produzindo `frontend/dist` com `index.html`, ativos Vite e diretórios de rotas como `inscricao`, `inscricaoAluno`, `administrativo`, `almoxarifado`, `agenda`, `comunicacao`, `conquistas` e `esporte-nas-regionais`.
- `npm run build` passou com Vite 7.3.3; houve apenas o aviso de base Browserslist desatualizada.
- A CLI oficial `cf` não está instalada nesta máquina. Nenhuma dependência foi instalada e nenhum deploy foi executado.
- Limitação atual: ainda não existe configuração Cloudflare no repositório; a escolha do fluxo de configuração/publicação fica para o próximo lote, mantendo o backend Spring separado.
- Próximo passo: verificar a configuração suportada pela CLI `cf` neste ambiente e preparar a publicação do frontend somente após confirmar o destino e a origem da API.

## Adaptação Cloudflare — lote 2 — 16/11/2026

- Consultada a documentação atual da CLI Cloudflare: projetos Vite novos usam `cloudflare.config.ts`; o fluxo não deve criar Wrangler neste repositório.
- Node `v24.22.0` foi confirmado, acima do mínimo documentado para carregar a configuração tipada.
- A CLI global `cf` foi instalada e responde em `1.0.0-rc.0`; login e deploy não foram executados.
- `cf init frontend --no-install` detectou Vite, saída `dist` e nome proposto `portal-esporte-web`, mas não concluiu porque tentou adicionar `cf` como dependência local e o npm retornou `EACCES` ao acessar o registry/cache.
- Nenhum `cloudflare.config.ts` ou configuração Wrangler foi criado. O `package.json` não recebeu alteração deste lote; as diferenças existentes são anteriores e foram preservadas.
- Próximo passo: decidir se a instalação local da dependência Cloudflare será repetida com permissões de npm corrigidas; só então gerar/revisar a configuração antes de `cf build --dry-run`.

## Objetivo ativo
- Issue #20 pronta para fechamento remoto: documentação de estrutura, rotas React/legadas e progresso atual validada contra `frontend/src/main.jsx`; `git diff --check` aprovado. O fechamento remoto será registrado separadamente no GitHub.
- Issue #18: os testes da inscrição foram alinhados ao fluxo atual. `inscricao-cards.test.mjs` cobre cards e diálogo; `inscricao-react.test.mjs` passou em 8/8, com o cenário antigo de ficha direta separado como legado. `npm run test:accessibility` passou em 12/12. A validação visual em navegador real permanece pendente por indisponibilidade do host.
- Issue #18: adicionada a cobertura `frontend/tests/inscricao-cards.test.mjs` para o fluxo atual da inscrição (cards Aluno, Instrutor e Núcleos, abertura da ficha do Aluno em diálogo e fechamento acessível). `npm run test:accessibility` passou em 12/12. Os 2 testes antigos de `inscricao-react.test.mjs` continuam fora da suíte consolidada porque ainda assumem a ficha aberta diretamente; a atualização deles fica separada da validação de acessibilidade.
- Issue #18: a suíte consolidada de acessibilidade permanece aprovada em 11/11 e o smoke de rotas React já passou. A regressão completa executada fora do sandbox passou em 142/144; os 2 testes restantes de `inscricao-react.test.mjs` refletem o contrato antigo de ficha aberta diretamente, enquanto a implementação atual abre a ficha pelo card Aluno em modal. Nenhum fluxo visual foi alterado neste lote. Próximo: atualizar esses testes para o fluxo atual ou validar manualmente no navegador quando o host estiver disponível.
- Issue #19: segunda rodada de pesquisa concluida sem novas coordenadas cadastraveis. A pagina oficial de Regionais fornece nomes e enderecos, e fontes municipais complementares confirmam alguns enderecos, mas os geocodificadores retornaram ruas, bairros ou locais diferentes sem correspondencia inequivoca com o equipamento. Nenhum centroide de rua foi convertido em pin. Os 21 locais seguem explicitamente pendentes; proximo passo depende de confirmacao municipal, levantamento cartografico oficial ou identificacao OSM do equipamento.
- Issue #19 em investigacao: a lista foi conferida contra `frontend/public/data/regionais.json`; os 9 pins verificados permanecem em `frontend/src/regionais/equipamentos-pins.json` e 21 equipamentos continuam sem posicao confirmada. Criado `frontend/tests/regionais-coordinates.test.mjs`, com `npm run test:regionais-coordinates` aprovado em 1/1, validando fontes OSM, IDs/coordenadas unicos e ausencia de pontos ficticios. Buscas em fontes oficiais confirmaram enderecos, mas nao forneceram coordenadas; o Nominatim recusou consultas automatizadas. Proximo: obter confirmacao de coordenadas por fonte municipal ou mapa verificavel antes de cadastrar novos pontos.
- Issue #20 alinhada localmente: `README.md`, `docs/progresso-issues.md` e `docs/rotas-portal.md` agora distinguem rotas React, compatibilidade legada, histórico de issues e estado remoto. Validação contra `frontend/src/main.jsx` passou e `git diff --check` não encontrou erros. A issue segue aberta até revisão final e fechamento remoto explícito.
- Issue #20 em resolucao: README atualizado para a estrutura real em `frontend/`, criada a tabela `docs/rotas-portal.md` com as rotas React montadas por `frontend/src/main.jsx`, e registrada a distinção entre migração concluída, compatibilidade legada e trabalho futuro. Ainda falta revisar o quadro histórico de progresso e conferir a consistência final dos documentos.
- Issue #18 em resolucao: criada a suite consolidada `npm run test:accessibility`, com 11/11 verificacoes aprovadas para modais, foco por teclado, anuncios de estado, movimento reduzido, responsividade, rotas e indicadores de foco. O smoke local de rotas React tambem passou e o build foi aprovado. A validacao visual/E2E em navegador real continua pendente porque o host do navegador esta indisponivel nesta sessao.
- Revisão das issues abertas: #16 foi corrigida localmente com histórico separado de devoluções, saldo acumulado e teste de múltiplas parcelas; `AlmoxarifadoApiTest` passou em 2/2. #17 (Docker/Nginx), #18 (validação visual/E2E), #19 (21 coordenadas) e #20 (documentação contraditória) continuam pendentes.
- Issue #17 em resolução: criado `frontend/tests/production-routes.test.mjs`, `ops/smoke-prod.ps1` e workflow manual `.github/workflows/production-smoke.yml`; teste local passou em 2/2 e a sintaxe PowerShell passou. Execução real do Compose continua pendente por Docker ausente.
- Issue #15 finalizada tecnicamente: `AlmoxarifadoApiTest` passou em 2/2, o build do frontend passou e `git diff --check` não apontou erros. A conferência visual automatizada permanece pendente porque o host do navegador esteve indisponível; o servidor local e a API foram validados separadamente.
- Issue #14 finalizada: `EventoApiTest` passou em 1/1, `agenda.test.mjs` passou em 11/11, o build do frontend passou e `git diff --check` não apontou erros.
- Issue #13 finalizada: validação da ficha de inscrição confirmada com `InscricaoImagemTest` em 8/8 testes, incluindo CPF, telefone, datas, responsável de menor e prevenção de persistência parcial. `git diff --check` aprovado.
- Issue #12 finalizada no escopo local: perfil `prod`, compose, scripts de backup/restauração e documentação revisados; simulação descartável restaurou banco, vídeo e imagem com sucesso. Limitação registrada: Docker não está instalado nesta sessão, portanto os containers não foram executados.
- Issue #9 finalizada: proteção CSRF validada no backend com 4/4 cenários e no cliente com `csrf.test.mjs` aprovado. O login permanece isento conforme o contrato de segurança.
- Corrigido o modal do Almoxarifado: ao abrir, o scroll do documento fica bloqueado, o scroll interno permanece disponível e o backdrop usa prioridade acima do cabeçalho. O bloqueio é restaurado ao fechar/desmontar. Build do frontend aprovado.
- Corrigido o carregamento do Almoxarifado no ambiente local: havia uma instância antiga do backend na porta 8080, sem `/api/almoxarifado`, causando erro após o login. A instância atual foi iniciada e validada com login `ROLE_ADMIN` e resposta `200 []` na listagem.
- Corrigido o bloqueio de login causado pelo interceptor CSRF global: `/login` agora é excluído explicitamente em `fetch` e `XMLHttpRequest`, conforme a isenção já configurada no backend. Testes de CSRF/login passaram (5/5) e o build do frontend passou.

- Lote atual da issue #15: a consulta de empréstimos/devoluções agora exibe total, itens em aberto e devolvidos, ordena pela data da entrega e mantém itens devolvidos bloqueados para nova devolução. O servidor local iniciou e o build permanece aprovado, mas a conferência visual automatizada não foi possível porque o host do navegador estava indisponível. Próximo: conferir manualmente no navegador.

## Issue #15 — persistência das movimentações do Almoxarifado — 16/11/2026

- Criado o núcleo persistente no backend para retiradas e devoluções: entidade `AlmoxarifadoMovimentacao`, repositório, serviço e endpoints em `/api/almoxarifado`.
- As movimentações registram tipo, material, quantidade, responsáveis, datas, estado, observações, autoria e status de devolução. A consulta e as mutações exigem perfil administrativo.
- Implementado controle otimista de concorrência com `@Version`: uma devolução enviada com versão antiga retorna conflito, evitando sobrescrever a atualização de outro operador.
- Fotos usam armazenamento dedicado configurável por `ALMOXARIFADO_FOTOS_DIR`, limite de 5 MB, validação de imagem, nome aleatório e endpoint próprio; falhas não confirmam a operação. O retorno do arquivo também possui fallback seguro de MIME.
- Verificação: `AlmoxarifadoApiTest` passou com 2/2 testes; `git diff --check` sem erros.
- Próximo lote: conectar os cards/formulários React do Almoxarifado a esse contrato, incluindo estados de carregamento, erro e preservação dos dados preenchidos.

## Issue #15 — lote React concluído — 16/11/2026

- Criado `frontend/src/almoxarifado/almoxarifado-service.js` com listagem, retirada, devolução e envio de fotos.
- `AlmoxarifadoPage` consulta a API ao abrir e exibe carregamento/erro sem remover os cards existentes.
- Verificações: `npm run build` passou e `git diff --check` passou.
- Próximo lote: criar os modais/formulários React para registrar retirada, devolução e anexar fotos.

## Issue #14 — Agenda compartilhada no backend — 16/11/2026

- O backend de eventos agora possui contrato completo para consulta pública por lista/id e gestão administrativa por criação, edição e exclusão. Eventos ganharam `categoria`, `hora`, `responsavel` e `materiais`, além dos campos existentes.
- `POST`, `PUT` e `DELETE` em `/api/eventos/**` exigem `ROLE_ADMIN`; `GET /api/eventos` e `GET /api/eventos/{id}` permanecem públicos. O identificador JPA é estável e os detalhes podem ser abertos por `?id=` sem depender de `localStorage`.
- A Agenda React passou a consultar e persistir no backend. Registros antigos de `eventosEsporte` só são enviados quando o usuário aciona explicitamente `Importar para a agenda compartilhada`; falhas mostram erro e não removem o armazenamento local.
- Criados `agenda-service.js`, `AgendaSharedPage.jsx`, `EventoSharedPage.jsx` e testes de conversão/agrupamento do contrato. A versão antiga da Agenda permanece preservada como referência e os testes legados continuam passando.
- Verificações: `EventoApiTest` passou; testes de agenda passaram com 13/13; teste de contrato API/frontend passou com 2/2; `npm run build` e `git diff --check` aprovados.
- Limitações: não foi validado com dois navegadores reais nem com uma conta administrativa real; migração de registros locais exige ação manual e cada criação retorna um novo identificador do servidor.

## Issue #13 — validação da ficha de inscrição na API — 16/11/2026

- `InscricaoService` agora valida no servidor os campos obrigatórios da ficha: participante, nascimento, modalidade, núcleo, data da declaração, assinatura e aceite da declaração. Nome e modalidade do envelope precisam coincidir com a ficha.
- CPF informado é validado com dígitos verificadores; telefone exige 10 ou 11 dígitos; datas inválidas, futuras ou incompatíveis são rejeitadas. Menores de 18 anos exigem nome, CPF e telefone do responsável, com CPF e telefone validados.
- A validação ocorre antes da criação e do `save`, evitando inscrição parcial. O consentimento de imagem continua aceitando `true`, `false` ou ausência, desde que a versão do termo permaneça coerente.
- Testes atualizados e ampliados em `backend/src/test/java/br/gov/betim/esporte/InscricaoImagemTest.java`, cobrindo consentimentos, CPF inválido, menor sem responsável e ausência de persistência. Verificação: `-Dtest=InscricaoImagemTest` passou; `git diff --check` sem erros.
- Limitações: a lista de modalidades e núcleos ainda é validada como texto não vazio; não foi feita integração com o banco auxiliar de núcleos nem validação em produção.

## Issue #12 — configuração de produção e recuperação de dados — 16/11/2026

- Criado `backend/src/main/resources/application-prod.properties`: `ddl-auto=validate`, console H2 e SQL detalhado desativados, credenciais externalizadas, cookie de sessão seguro/configurável e diretórios de dados/uploads definidos por ambiente.
- Criado `docker-compose.prod.yml` com volumes nomeados separados para banco, vídeos e imagens, perfil `prod`, reinício automático e variáveis obrigatórias de credenciais.
- Criados `ops/backup-portal.ps1` e `ops/restore-portal.ps1`; o backup inclui banco/cache e uploads, gera pasta datada e `SHA256SUMS.json`, e a restauração exige confirmação explícita (ou `-Force` somente para automação controlada).
- Criado `docs/operacao-producao.md` com configuração, estratégia `validate` + migrações SQL revisadas, procedimento de backup/restauração e validações pós-restauração.
- Verificações: backup e restauração executados em diretórios temporários descartáveis, conferindo banco, vídeo e imagem; `git diff --check` sem erros.
- Limitações: Docker não está instalado nesta sessão, portanto `docker compose config` e a execução dos containers não foram verificadas; não houve acesso nem alteração de produção.

## Issue #9 — proteção CSRF nas operações autenticadas — 16/11/2026

- Ativada a proteção CSRF do Spring Security com `CookieCsrfTokenRepository`: o backend publica `XSRF-TOKEN` e valida o header `X-XSRF-TOKEN` em mutações. O processamento de login e os POSTs públicos de inscrição/suporte permanecem fora do filtro porque ainda não dependem de sessão autenticada.
- Criado `GET /api/auth/csrf` e testes para token publicado, ausência, token inválido e token válido avançando até a autorização. Testes existentes de fotos, conquistas, regionais e horto foram ajustados para enviar CSRF em operações protegidas; adicionada a dependência de teste do Spring Security.
- O cliente global em `frontend/public/js/portal-header.js` obtém o token e injeta o header automaticamente em `fetch` e `XMLHttpRequest`, cobrindo chamadas React, páginas legadas e uploads.
- Verificações: `CsrfApiTest` passou com 4/4; testes frontend do cliente CSRF e cabeçalho passaram com 8/8; `git diff --check` sem erros. A suíte backend completa deve ser repetida em processo isolado se houver disputa pelo banco H2 operacional compartilhado.
- Limitações: não foi realizado teste contra produção nem validação visual; o contrato ainda depende de o frontend ser servido pela mesma origem da API ou de configuração de cookie/CORS compatível.

## Detalhe de modalidade legado concluído — 16/11/2026

- `detalhe-modalidade.html` já estava coberto pelo catálogo React como o alias legado da modalidade Judô (`legacyHref` em `frontend/src/modalidades/catalog.js`). A rota compartilhada resolve o endereço antigo para `/judo`, e `ModalidadePage` renderiza conteúdo, horários, núcleos, descrição e galeria oficial a partir do conteúdo React.
- Verificações: `npm run build` aprovado; `node --test tests/modalidades-react.test.mjs` passou com 3/3; o artefato `dist/detalhe-modalidade.html` foi conferido redirecionando para `/judo`.
- Nenhum componente duplicado foi criado e o HTML legado permanece apenas como compatibilidade de entrada.
- Limitação: validação visual/responsiva no navegador não realizada nesta sessão.

## Almoxarifado adaptado para React — 16/11/2026

- `frontend/almoxarifado.html` preservado como entrada legada e integrado a `/almoxarifado` com componente React no portal existente.
- Mantidos `localStorage` (`almoxarifadoSemes`), catalogo de 15 materiais, retirada, devolucao, fotos locais, impressao, limpeza com confirmacao e tabela de movimentacoes.
- Aplicados cabecalho/rodape compartilhados, tokens do portal, foco visivel, labels, responsividade e estilo de impressao.
- Verificacoes: `npm run build` aprovado; `node --test tests/*.test.mjs` aprovado com 127 testes; `/almoxarifado` HTTP 200; `almoxarifado.html?origem=menu` redirecionou para `/almoxarifado?origem=menu`.
- Limitacao: conferencia visual e interacao real no navegador nao foram executadas nesta sessao.

## Almoxarifado limpo para recriacao — 16/11/2026

- Removidos do componente React todos os elementos abaixo do cabecalho proprio de Almoxarifado: resumo, formularios, historico, botoes e interacoes locais.
- Rota `/almoxarifado` e compatibilidade de `almoxarifado.html` preservadas; APIs, autenticacao e outras paginas nao foram alteradas.
- Verificacao: `npm run build` aprovado; rota limpa HTTP 200 e entrada legada redirecionando para `/almoxarifado?origem=menu`.
- Proximo passo: recriar o conteudo da pagina conforme a proxima orientacao do usuario.

## Cards iniciais do almoxarifado — 16/11/2026

- Criados tres cards visuais abaixo do cabecalho: Entrada, Saida e Historico.
- Cards ainda nao possuem navegacao ou operacao; aguardam a definicao do conteudo de cada fluxo.
- Verificacao: `npm run build` aprovado e `/almoxarifado` respondeu HTTP 200.

## Icones dos cards do almoxarifado — 16/11/2026

- Entrada usa caixa com seta para baixo (`fa-box-arrow-down`), Saida usa caixa com seta para cima (`fa-box-arrow-up`) e Historico usa livro (`fa-book`).
- Verificacao: `npm run build` aprovado.

## Icone do card Instrutor — 16/11/2026

- Adicionado icone inline de pessoa diante de quadro ao card `Instrutor`, sem depender de classe externa.
- Verificacao: `npm run build` aprovado.

## Banco auxiliar de cadastro de nucleos — 16/11/2026

- Analisada a planilha `TABELAS BI 11-26 (1).xlsx`, com seis abas e dados de regionais, nucleos, modalidades, matriculados e ofertas.
- Criado `database/nucleos_mysql.sql`, banco MySQL isolado `portal_esporte_nucleos`, sem integração com o portal.
- Criado `database/README-nucleos.md` com modelo, carga inicial, validação esperada e limites da etapa.
- Modelo normalizado em `regionais`, `nucleos`, `modalidades` e `nucleos_modalidades`; carga inicial prevista: 11 regionais, 30 nucleos, 25 modalidades e 95 ofertas.
- Verificacoes: contagem dos registros da fonte conferida; 95 relações núcleo–modalidade presentes no SQL; `git diff --check` aprovado. Cliente MySQL não está instalado nesta sessão, então a execução em servidor MySQL permanece pendente.
- O backend, frontend, rotas, autenticação e banco H2 do portal não foram alterados.

## Card de Quadras removido do Administrativo — 16/11/2026

- Removido o card `Quadras do Horto` do componente React e da entrada HTML legada de Administrativo.
- A rota independente `/agendamento-quadras` foi preservada; somente o atalho visual da área Administrativa foi retirado.
- Verificações: testes administrativos confirmam os 12 documentos e a ausência do card; build e testes frontend executados nesta etapa.

## Revisao do uso de React — 16/11/2026

- Conferido `frontend/src/main.jsx`, `vite.config.js`, `scripts/copy-html-pages.mjs` e a árvore de páginas HTML.
- Rotas canônicas de Home, modalidades, Regionais, Conquistas, Login, Administrativo, Inscrição e Almoxarifado usam o entrypoint React.
- Agenda, agendamento de quadras, comunicação, detalhes de evento, downloads, frequência, importação de imagens/vídeos e presença continuam em HTML/JavaScript legado.
- Arquivos HTML das rotas migradas permanecem como compatibilidade ou fonte legada; isso não significa que a rota canônica esteja sendo renderizada por eles.

## Agenda adaptada para React — 16/11/2026

- Criado `frontend/src/agenda/AgendaPage.jsx` com calendário, navegação mensal, destaque do dia atual, eventos, modal de criação/edição/exclusão, impressão e persistência em `localStorage`.
- Criado `frontend/src/agenda/agenda.css` com visual responsivo, foco, impressão e tokens visuais alinhados ao portal.
- Rota canônica `/agenda` adicionada ao React; `agenda.html` preservado como redirecionamento compatível no desenvolvimento e no build.
- Mantido o armazenamento `eventosEsporte` e o encaminhamento de detalhes por `eventoSelecionado` para `detalhes-evento.html`.
- Verificações: `node --test tests/agenda.test.mjs` com 11 testes aprovados; `npm run build` aprovado; `dist/agenda/index.html` gerado e `dist/agenda.html` redirecionando para `/agenda`.

## Agendamento de quadras adaptado para React — 16/11/2026

- Criado `frontend/src/horto/HortoPage.jsx`, preservando a verificação de `ROLE_ADMIN`, as APIs `/api/auth/me`, `/api/horto/quadras`, `/api/horto/reservas` e `/api/horto/esportes`, seleção de quadra, horários, filtros por tipo de piso, confirmação e tabela de reservas.
- A rota canônica `/agendamento-quadras` passou a usar React; `agendamento-quadras.html` permanece como redirecionamento compatível no desenvolvimento e no build.
- Reutilizado o stylesheet existente `public/css/agendamento-quadras.css`; detalhes, autenticação, limites de data e regras de conflito foram preservados.
- Verificações: `node --test tests/horto-utils.test.mjs` com 6 testes aprovados; `npm run build` aprovado; entrada `dist/agendamento-quadras/index.html` gerada e redirecionamento legado verificado.

## Central de arquivos adaptada para React — 16/11/2026

- Criado `frontend/src/comunicacao/ArquivosPage.jsx` com os cards de importação de vídeos, importação de imagens e fotos oficiais automáticas.
- Preservados os links de importação, consulta de `/api/fotos/status`, atualização manual somente para `ROLE_ADMIN` e compatibilidade de `comunicacao.html`.
- Criado `frontend/src/comunicacao/arquivos.css` com visual responsivo, foco visível e cards alinhados ao portal.
- Verificações: `node --test tests/fotos.test.mjs` com 5 testes aprovados; `npm run build` aprovado; entrada `dist/comunicacao/index.html` gerada e redirecionamento legado verificado.

## Detalhes do evento adaptados para React — 16/11/2026

- Criado `frontend/src/agenda/EventoPage.jsx` para ler `eventoSelecionado` do `localStorage` e renderizar título, categoria, data, hora, local, responsável, descrição, materiais e orientações.
- Conteúdo de evento passou a ser renderizado com React, sem interpolação HTML; dados ausentes ou JSON inválido exibem mensagem segura.
- Preservados voltar, imprimir e o link para `/almoxarifado`; `detalhes-evento.html` continua compatível e redireciona para `/detalhes-evento`.
- Verificações: `node --test tests/agenda.test.mjs` com 11 testes aprovados; `npm run build` aprovado; entrada `dist/detalhes-evento/index.html` gerada e redirecionamento legado verificado.

## Downloads adaptados para React — 16/11/2026

- Criado `frontend/src/downloads/DownloadsPage.jsx` com os quatro documentos oficiais, links de download e instruções de entrega.
- Corrigido no novo componente o nome do arquivo do protocolo de vídeos e fotos para corresponder ao PDF existente no projeto.
- Criado `frontend/src/downloads/downloads.css` com grade responsiva, estados de foco e cartões alinhados ao portal.
- A rota canônica `/downloads` passou a usar React; `downloads.html` continua compatível e redireciona no desenvolvimento e no build.
- Verificações: `npm run build` aprovado; `dist/downloads/index.html` gerado; redirecionamento legado verificado; os quatro PDFs da página encontrados no projeto.

## Frequência adaptada para React — 16/11/2026

- Criado `frontend/src/frequencia/FrequenciaPage.jsx` com seleção de data, turma, instrutor, lista de atletas e estados de presença, falta e falta justificada.
- Criado `frontend/src/frequencia/frequencia.css` com resumo de contagens, tabela responsiva, foco visível e visual alinhado ao portal.
- A rota canônica `/frequenciapreenchimento` passou a usar React; `frequenciapreenchimento.html` continua compatível e redireciona.
- O Firebase do HTML original tinha apenas credenciais-placeholder; por isso o lançamento permanece local nesta etapa e informa que o envio ao banco será conectado depois.
- Verificações: `npm run build` aprovado; `dist/frequenciapreenchimento/index.html` gerado; redirecionamento legado verificado.

## Importação de imagens adaptada para React — 16/11/2026

- Criado `frontend/src/uploads/ImportarImagensPage.jsx` com seleção múltipla, envio sequencial, progresso por arquivo, mensagens de sucesso/erro e galeria das imagens importadas.
- Preservados os endpoints `GET /api/imagens` e `POST /api/imagens`, o campo multipart `arquivo`, credenciais da sessão e limite/formato aplicados pelo backend.
- Criado `frontend/src/uploads/uploads.css` com visual responsivo e foco visível; a rota canônica `/importar-imagens` foi adicionada e `importar-imagens.html` continua compatível.
- Verificações: `npm run build` aprovado; `dist/importar-imagens/index.html` gerado; redirecionamento legado verificado.

## Importação de vídeos adaptada para React — 16/11/2026

- Criado `frontend/src/uploads/ImportarVideosPage.jsx` com seleção de vídeo, upload via `POST /api/videos`, progresso, mensagens e listagem dos vídeos importados via `GET /api/videos`.
- Preservados o campo multipart `arquivo`, credenciais da sessão, formatos aceitos pelo backend e limite de 200 MB aplicado pela API.
- Criado `frontend/src/uploads/videos.css`; a rota canônica `/importar-videos` foi adicionada e `importar-videos.html` continua compatível.
- Verificações: `npm run build` aprovado; `dist/importar-videos/index.html` gerado; redirecionamento legado verificado.

## Relatório de presença adaptado para React — 16/11/2026

- Criado `frontend/src/presenca/PresencaPage.jsx` com os dados mensais de modalidade, instrutor, núcleo, alunos e estados P/F/FJ do relatório original.
- Criado `frontend/src/presenca/presenca.css` com tabela responsiva, legenda, assinaturas, foco visível e regras de impressão.
- A rota canônica `/presenca` passou a usar React; `presenca.html` continua compatível e redireciona.
- Nenhuma API foi inventada ou alterada: o relatório original era estático e continua sendo apresentado como relatório estático.
- Verificações: `npm run build` aprovado; `dist/presenca/index.html` gerado; redirecionamento legado verificado.

## Página geral antiga encaminhada para a Home React — 16/11/2026

- Identificado que `esporte.html` era uma cópia antiga da Home, com carrossel, notícias, mapa e navegação próprios, divergentes da Home oficial em React.
- O endereço legado agora redireciona para `/`, preservando query string e fragmento no build; a rota de desenvolvimento também redireciona.
- Nenhuma segunda implementação foi criada e o conteúdo da Home React não foi alterado.
- Verificação: `npm run build` aprovado; `dist/esporte.html` confirmado com `location.replace("/"...)`.

## Página esporteteste encaminhada para a Home React — 16/11/2026

- Identificado que `esporteteste.html` era outra variação antiga da Home, com seções e scripts duplicados.
- O endereço legado agora redireciona para `/`, preservando query string e fragmento no build; a rota de desenvolvimento também redireciona.
- A Home oficial em React permaneceu como única implementação ativa.
- Verificação: `npm run build` aprovado; `dist/esporteteste.html` confirmado com `location.replace("/"...)`.

## Páginas de teste e cópia encaminhadas para a Home React — 16/11/2026

- Identificados os arquivos `index - Copia.html`, `index - Copia1.html`, `teste28.html` e `teste28 (2).html` como variações de teste/cópia sem rota oficial.
- Todos os endereços legados agora redirecionam para `/` no desenvolvimento e no build, evitando manter implementações paralelas.
- Verificações: `npm run build` aprovado; os quatro arquivos publicados foram substituídos por redirecionamentos para a Home.

## Capi adaptada para React — 16/11/2026

- Criado `frontend/src/capi/CapiPage.jsx` com a mascote local, frases automáticas, interação por clique, espelhamento conforme o mouse e limpeza de timers/listeners no desmontar.
- Criado `frontend/src/capi/capi.css` com animação, acessibilidade de foco e suporte a movimento reduzido.
- A rota canônica `/capi` passou a usar React; `capi.html` continua compatível e redireciona.
- Verificações: `npm run build` aprovado; `dist/capi/index.html` gerado; redirecionamento legado verificado.

## Descanso adaptado para React — 16/11/2026

- Criado `frontend/src/descanso/DescansoPage.jsx` com a animação Matrix em canvas, texto BETA, redimensionamento responsivo e ciclo `requestAnimationFrame`.
- Criado `frontend/src/descanso/descanso.css` com fundo visual, altura da página e preferência de movimento reduzido.
- A rota canônica `/descanso` passou a usar React; `descanso.html` continua compatível e redireciona.
- Verificações: `npm run build` aprovado; `dist/descanso/index.html` gerado; redirecionamento legado verificado.

## Cards da inscricao com fundo padronizado — 16/11/2026

- O card clicavel `Aluno` recebeu o mesmo fundo branco, borda e cor dos cards `Instrutor` e `Nucleos`.
- O comportamento de abertura da ficha do aluno foi preservado.
- Verificacao: `npm run build` aprovado.

## Cabecalho da inscricao simplificado — 16/11/2026

- O cabecalho agora exibe somente `Ficha de inscrição`; subtitulo, aviso e identificacao complementar foram removidos.
- Os cards Aluno, Instrutor e Nucleos foram preservados.
- Verificacao: `npm run build` aprovado.

## Endereco alternativo da inscricao — 16/11/2026

- A mesma pagina React de inscricao passou a estar disponivel em `/inscricaoAluno` e no build em `dist/inscricaoAluno/index.html`.
- `/inscricao`, `Inscricao.html` e `inscricao.html` foram preservados para compatibilidade; conteudo, API `/api/inscricoes` e autenticacao nao foram alterados.
- Verificacao: `npm run build` aprovado; `/inscricaoAluno` respondeu HTTP 200.

## Pagina de inscricao recriada com tres cards — 16/11/2026

- Removido todo o conteudo abaixo do cabecalho da ficha e adicionados os cards `Aluno`, `Instrutor` e `Nucleos`.
- Rotas, compatibilidade de `Inscricao.html`/`inscricao.html`, servicos e API foram preservados no projeto, mas o formulario nao e renderizado nesta etapa.
- Verificacao: `npm run build` aprovado; `/inscricaoAluno` respondeu HTTP 200.
- Pendencia: `tests/inscricao-react.test.mjs` ainda contem expectativas da ficha completa e precisa ser atualizado para os tres cards antes de voltar a integrar o formulario.

## Ficha do aluno em aba flutuante — 16/11/2026

- O card `Aluno` agora abre a ficha baseada na planilha `Ficha_Inscricao_Programa_Viva_Esportes (1).xlsx` em um modal React flutuante.
- O modal contem as cinco secoes preenchiveis da ficha, botao `X`, Fechar, Escape e fechamento ao clicar no fundo.
- O botao `Salvar ficha` apenas confirma o preenchimento local e informa que o envio sera conectado na proxima etapa; nenhuma API foi chamada.
- Verificacao: `npm run build` aprovado; `/inscricaoAluno` respondeu HTTP 200. Conferencia visual interativa em navegador permanece pendente.

## Cards da secao Acao em Video reduzidos — 16/11/2026

- Reduzidos em 50% os limites de largura dos dois videos principais, de 400px para 200px, e da coluna de videos menores, de 200px para 100px.
- Controles, fontes, proporcao e demais secoes da Home foram preservados.
- Verificacao: `npm run build` aprovado. Conferencia visual em navegador permanece pendente.

## Restauracao do tamanho original dos videos — 16/11/2026

- Desfeita a reducao da secao `Acao em Video`: videos principais restaurados para 400px e coluna menor para 200px.
- Verificacao: `npm run build` aprovado.

## Remocao global do chat de suporte — 16/11/2026

- Removido do `portal-header.js` o carregamento global de `portal-suporte.js` e `portal-suporte.css`; nenhuma pagina injeta mais o chat.
- API, cliente e base de conhecimento de suporte foram preservados, sem interface ativa no portal.
- Teste `node --test tests/suporte.test.mjs`: 8 aprovados. `npm run build`: aprovado.

## Chat de suporte removido do almoxarifado — 16/11/2026

- O injetor global agora nao carrega o CSS nem o script do chat quando a rota e `/almoxarifado` ou `almoxarifado.html`.
- O chat permanece habilitado nas demais paginas do portal.
- Verificacao: `npm run build` aprovado.

## Card de relatorios e indicadores — 16/11/2026

- Adicionado o card `Relatórios e indicadores`, com icone inline de grafico.
- O card permanece visual, sem dados ou navegacao inventados.
- Verificacao: `npm run build` aprovado.

## Card de distribuicao por unidade esportiva — 16/11/2026

- Adicionado o card `Distribuição por unidade esportiva`, com icone inline de unidades prediais.
- O card permanece visual, sem navegacao ou fluxo operacional definido.
- Verificacao: `npm run build` aprovado.

## Remocao da descricao do card de movimentacoes — 16/11/2026

- Removida a descricao do card `Entradas e saídas`; titulo, icone e demais cards preservados.
- Verificacao: `npm run build` aprovado.

## Card unificado de Entradas e saidas — 16/11/2026

- Entrada e Saida foram unificadas no card `Entradas e saídas`, com icone de caixa bidirecional.
- Incluida a orientacao: registrar recebimentos, distribuicoes, transferencias e baixas, identificando o responsavel por cada movimentacao.
- Permanecem separados os cards de emprestimo/devolucoes e cadastro de materiais.
- Verificacao: `npm run build` aprovado.

## Grade com tres cards por linha — 16/11/2026

- A grade do almoxarifado foi ajustada para tres cards por linha em telas largas; o quarto card quebra para a linha seguinte.
- Verificacao: `npm run build` aprovado.

## Card de cadastro de materiais — 16/11/2026

- Adicionado o quarto card visual `Cadastro de materiais`, com icone de formulario e cor verde institucional.
- Grade ajustada para quatro colunas em telas largas, duas em telas intermediarias e uma no celular.
- Verificacao: `npm run build` aprovado.

## Renomeacao do card de historico — 16/11/2026

- O terceiro card agora exibe `Empréstimo e devoluções`; icone de livro e classe estrutural foram preservados.
- Verificacao: `npm run build` aprovado.

## Correção dos icones de Entrada e Saida — 16/11/2026

- Substituidas as classes Font Awesome inexistentes por SVGs inline de caixa com seta para baixo e para cima; o livro do Historico foi preservado.
- Verificacao: `npm run build` aprovado.

Adaptar diretamente o portal existente aos padrões da equipe, em lotes pequenos,
preservando funcionalidades, APIs, sessão, banco e uploads. Usar sempre a skill
`.agents/skills/decompor-tarefas/SKILL.md` e perguntar antes de iniciar outro lote.

## Referências e decisões vigentes

- Padrões: `C:/Users/walter/Desktop/Sistemas/Desygn-sistem/.design-system`.
- Relatório e histórico: `docs/adaptacao-design-system.md`.
- Destino das mudanças: `frontend/` existente com Vite; piloto em `frontend/piloto/` não é o destino do trabalho.
- Último endereço confirmado do portal: `http://localhost:5174`; verificar disponibilidade ao retomar.
- Preferência explícita: cabeçalho azul, com marcas em `#023568` e navegação em `#0072e7`, em vez da faixa verde do kit.
- Preservar menus por hover, Inscreva-se oculto e botão Entrar visível para visitantes, seis conquistas por ano, notícias de Betim e descrição apenas no marco da timeline.
- Preservar o indicador de 1.452 medalhas e todas as integrações existentes.

## Último ponto concluído

- Home com atalhos de navegação, alvos focáveis, um h1 e uma única área principal.
- Cabeçalho compartilhado com ajustes de proporção dos logos, foco e legibilidade.
- Cabeçalho permanece azul conforme solicitado, com acentos dos painéis em azul.
- Barra de acessibilidade integrada às 21 páginas compartilhadas: A+, A−, Contraste e Restaurar.
- Preferências `fontSize` (70%–140%) e `theme` persistidas e aplicadas no head antes da renderização; funcionamento sem armazenamento e sincronização entre abas verificados.
- Alto contraste implementado como ponte CSS nas páginas atuais, sem inverter fotos ou vídeos. Ainda precisa de validação visual em navegador, incluindo formulários e mapa.
- Verificações atuais: 75 testes aprovados, build Vite aprovado e recursos servidos na porta 5174.
- Comando `:return` registrado em AGENTS.md e nos adaptadores para outros assistentes.
- O componente React oficial de acessibilidade permanece apenas no piloto; o site Vite usa uma adaptação compatível com a escala e as chaves do kit.

## Próximo lote ao receber :return ou autorização de continuidade

Adaptar o rodapé no portal existente, começando pela conferência dos contatos e
órgãos responsáveis e pela leitura do componente oficial. Não inventar dados de contato.

## Atualização adicional — runtime Java (11/11/2026)

- Backend atualizado para Java 25 LTS e Spring Boot 3.5.16, compatível com Java 25.
- Imagens de build e execução do backend atualizadas para Eclipse Temurin 25; README alinhado.
- Verificações: `clean test-compile` e `clean test` aprovados com Temurin 25.0.5; 71 testes passaram.
- Limitações: a validação CVE não pôde ser executada porque a ferramenta retornou indisponibilidade de modelo; Docker não está instalado/disponível para validar a imagem.
- Próximo passo deste ajuste: repetir a verificação CVE quando a ferramenta estiver disponível. O próximo lote da adaptação do portal permanece o rodapé descrito acima.

## Inicialização local — 11/11/2026

- Frontend iniciado com npm run dev -- --host 127.0.0.1 --port 5174; página inicial respondeu HTTP 200.
- API já existente na porta 8080 (PID 13476); /login respondeu HTTP 401. Tentativa de segunda instância encerrada por bloqueio do banco H2, sem encerrar a instância existente.
- Nenhuma mudança funcional solicitada; próximo lote de adaptação permanece o rodapé. Login autenticado não verificado.


## Cards de indicadores — 11/11/2026

- A pedido do usuário, os seis cards abaixo do carrossel seguem a composição da imagem: três colunas por duas linhas, círculos de ícones sobrepostos, cantos de 16px e conteúdo centralizado.
- Gradiente azul preserva a identidade vigente; os seis títulos e valores, incluindo 1.452 Medalhas, e o link para conquistas.html foram mantidos. Sem fotos, contagens ou destinos inventados.
- Alterados apenas Indicators em frontend/src/home/Sections.jsx e estilos locais em frontend/src/home/home.css, além deste registro. Grade com duas colunas abaixo de 768px e uma abaixo de 480px; foco visível, alto contraste e movimento reduzido contemplados.
- Verificações: 75 testes aprovados, build Vite aprovado e módulo atualizado servido com HTTP 200 na porta 5174.
- Limitação: navegador indisponível na sessão; apresentação em desktop/celular e escala de fonte ainda precisam de conferência visual.
- Próximo passo deste ajuste: conferir visualmente os cards com o usuário. O rodapé continua como lote separado, mediante autorização de continuidade.

## Altura do card Pilates — 11/11/2026

- Aplicado h-full ao painel interno do link Pilates em frontend/src/home/Sections.jsx; o painel passa a preencher a altura da célula da grade, acompanhando os cards vizinhos sem altura fixa.
- Texto, link e demais cards preservados. Build aprovado e utilitário .h-full com height:100% confirmado no CSS compilado.
- Validação visual ainda pendente por indisponibilidade do navegador nesta sessão. Próximo lote de adaptação permanece separado deste ajuste.

## Remoção de Esporte de Rendimento — 11/11/2026

- Removidos o componente Performance e sua renderização/importação na home existente.
- Retirada a entrada Rendimento do menu Programas e sua descrição; páginas individuais das modalidades preservadas.
- Catálogo do suporte regenerado sem a seção removida (67 referências); teste da home atualizado para verificar a ausência da seção e manter as demais verificações.
- Verificações: build Vite aprovado e 75 testes aprovados, zero falhas. Validação visual não executada por indisponibilidade do navegador na sessão.
- Pedido concluído; próximo lote geral permanece o rodapé, mediante autorização de continuidade.

## Escola de Esportes: pesquisa e carrossel — 11/11/2026

- Pesquisa aprofundada no site oficial documentada em docs/pesquisa-modalidades.md. A contagem oficial diverge: Programas informa 21 no PVE; relação por núcleo permite consolidar 20; notícias confirmam Natação e Ginástica Rítmica competitivas.
- Carrossel reúne 22 atividades documentadas, com as duas equipes competitivas identificadas. Não equivale a confirmar 22 turmas abertas da Escola. Tênis e Karatê anteriores não confirmados e substituídos pela relação pesquisada.
- SchoolCarousel.jsx integrado à seção Escola no portal existente; avanço de um card a cada 3 segundos, controles manuais, pausa, hover/foco, aba oculta e movimento reduzido; layout responsivo. Link oficial por núcleo incluído.
- Verificações: build aprovado; 76 testes na suíte geral e três testes adicionais do comportamento do carrossel aprovados (79 no total). Aparência/toque/animação em navegador não verificados, pois navegador indisponível.
- Próximo passo: conferir visualmente com o usuário e validar a lista administrativa caso seja necessário afirmar exatamente quais são as 22 modalidades da Escola. Rodapé permanece um lote separado.

## Rotação automática da Escola — 11/11/2026

- Ajustada conforme pedido explícito: inicia automaticamente a cada 3 segundos e continua com mouse/foco no carrossel. Pausar/Retomar funciona independentemente do foco no botão.
- Preferência por movimento reduzido mantém a troca automática, sem animação; aba oculta não avança. Este ajuste substitui a regra anterior de pausa por hover/foco/movimento reduzido.
- Verificações: nove testes de home/carrossel aprovados e build aprovado. Validação visual permanece pendente por navegador indisponível.
- Próximo passo: conferir a rotação no portal; nenhuma outra alteração neste lote.

## Carrossel sem botões e barra — 11/11/2026

- Removidos os três botões acima das modalidades e seus estilos. Barra de rolagem ocultada, preservando rolagem por toque/teclado e avanço automático a cada 3 segundos.
- Pausa/retomada acessível pela tecla espaço quando a lista recebe foco; instrução incluída no nome acessível.
- Verificações: nove testes de home/carrossel e build aprovados. Conferência visual em navegador permanece pendente.
- Próximo passo: conferir o resultado no portal; demais lotes aguardam orientação.

## Setas transparentes no carrossel — 11/11/2026

- Adicionadas setas anterior/próxima nas laterais, com fundo transparente e ícones discretos; ficam mais visíveis no hover/foco. Espaço lateral reservado para não cobrir cards.
- Rotação automática, barra oculta, modalidades e pausa por espaço preservadas. Botões com nomes acessíveis e foco visível.
- Verificações: nove testes de home/carrossel e build aprovados. Aparência em navegador ainda não verificada por indisponibilidade na sessão.
- Próximo passo: conferir visualmente as setas no portal; outros lotes aguardam orientação.

## Espaçamento das setas — 11/11/2026

- Reserva lateral do carrossel ampliada de 40px para 56px, criando 16px de distância entre cada seta e os cards.
- Alteração somente de espaçamento em home.css. Build aprovado; conferência visual em navegador permanece pendente.
- Próximo passo: conferir o espaçamento no portal; nenhuma outra alteração neste lote.

## Cards da Escola com páginas específicas — 11/11/2026

- Os 22 cards agora são links completos e distintos, com foco visível, preservando setas, distância de 16px, barra oculta e avanço automático.
- Catálogo central em frontend/public/js/escola-modalidades.js reúne nomes, ícones, destinos e núcleos confirmados. Reutilizados futebol.html, volei.html, pilates.html e detalhe-modalidade.html (Judô), sem editar seu conteúdo.
- Criadas 18 páginas HTML próprias para os demais destinos, com cabeçalho compartilhado, preferências de acessibilidade, título, núcleos oficiais quando publicados, fonte e retorno à Escola. Natação/Ginástica Rítmica mantêm identificação de equipe competitiva e não anunciam seleção aberta.
- Script frontend/scripts/create-school-pages.mjs cria somente páginas faltantes sem sobrescrever existentes. CSS das páginas em public/css/modalidade-escola.css; novas páginas são incluídas pelo mecanismo existente de cópia de HTML no build.
- Fontes: relação oficial por núcleo para 2026 e notícias já documentadas em docs/pesquisa-modalidades.md; horários, vagas e critérios específicos não foram inventados. Não houve alteração em APIs, banco ou páginas das modalidades já existentes.
- Verificações: 81 testes aprovados; build aprovado; os 22 destinos responderam HTTP 200, com conteúdo, e estão presentes no dist. Testes de sessão/preferências atualizados para as 39 páginas compartilhadas.
- Limitação: aparência e clique em navegador não verificados por indisponibilidade do navegador nesta sessão.
- Próximo passo: conferir visualmente os cards e as novas páginas; conteúdo mais detalhado por modalidade pode ser tratado em lote posterior mediante orientação.

## Cards laterais menores — 11/11/2026

- Escala dos cards calculada pela distância ao centro visível: 100% no centro e até 76% nas extremidades, simetricamente. Atualiza durante a rolagem e ao redimensionar.
- Avanço usa a largura real de layout para não ser afetado pela escala. No celular, alinhamento central com respiro lateral; links e setas preservados.
- Verificações: dez testes de home/carrossel aprovados, incluindo escala simétrica e avanço; build aprovado. Conferência visual permanece pendente por navegador indisponível.
- Próximo passo: conferir visualmente o destaque central com o usuário.

## Indicadores finais padronizados — 11/11/2026

- Seis cards finais adaptados aos tokens do portal: ícones e números azuis, fonte Karla sem itálico, cantos/sombra/borda institucionais e acento amarelo.
- Preservados todos os números e rótulos. Removida a proibição de quebra de linha; rótulos com largura limitada, quebra balanceada e tamanho de 1rem. Grade com seis colunas em telas largas, três/duas/uma conforme a largura.
- Alterados FinalIndicators em Sections.jsx e CSS local da home. Cinco testes da home e build aprovados.
- Limitação: encaixe visual real não conferido por navegador indisponível na sessão. Próximo passo: conferir no portal, incluindo escala de fonte ampliada.

## Viva o Esporte compacto — 11/11/2026

- Reduzidos espaçamento interno para 24px, título para 1,5–2rem e intervalos dos parágrafos; retirada a quebra forçada do título.
- Coluna de texto ocupa 60% no desktop para reduzir a altura sem cortar conteúdo. Altura mínima da foto reduzida de 500px para 320px no desktop e 280px em telas menores.
- Textos, imagem, créditos e fonte preservados. Cinco testes da home e build aprovados. Validação visual pendente por navegador indisponível.
- Próximo passo: conferir o tamanho do card no portal.

## API e rotação das fotos Viva o Esporte — 11/11/2026

- Nova rota pública GET /api/fotos/viva-esporte em FotosController: atualizadoEm, intervaloSegundos=9 e até cinco fotos únicas da categoria atividades, aproveitando o acervo oficial já validado/cacheado da Prefeitura. Sem consultas externas a cada troca.
- VivaPhotos.jsx integrado ao card compacto: seleção validada, troca a cada 9000ms, créditos/link sincronizados com a foto, botão de pausa, sem avanço em aba oculta, consulta da seleção a cada cinco minutos. Mantém a seleção em erro de rede, remove imagens que falham e usa a imagem oficial original quando não há opções.
- Nova API ativada reiniciando somente o processo Java local identificado como PortalEsporteApplication; banco existente preservado.
- Verificações: cinco testes FotosApiTest aprovados; 85 testes frontend aprovados; build aprovado. API pelo proxy 5174 retornou HTTP 200, cinco fotos e intervalo 9; as cinco imagens tiveram HTTP 200 e tipo image/*.
- Uma verificação antiga do frontend esperava atributos do componente anterior; atualizada para verificar a integração VivaPhotos e a nova rota, com suíte completa aprovada após ajuste.
- Limitação: rotação visual em navegador não conferida por navegador indisponível. Próximo passo: conferir as fotos no card e seus créditos; demais lotes separados.

## Viva o Esporte com overlay e desfoque — 11/11/2026

- Card passou de duas colunas para foto oficial cobrindo o fundo; texto branco à esquerda sobre gradiente azul e desfoque suave com máscara que desaparece em direção à direita, conforme referência do usuário.
- Todo o texto foi preservado. Em telas menores, a camada azul cobre a largura para manter legibilidade; créditos ficam abaixo do texto e a pausa permanece no topo direito. Alto contraste com fundo preto sem desfoque.
- Alteração somente no CSS da home; API, seleção de até cinco fotos, intervalo de nove segundos e créditos preservados.
- Verificações: oito testes de home/fotos e build aprovados. Conferência visual pendente por navegador indisponível na sessão.
- Próximo passo: conferir overlay, enquadramento e legibilidade no portal.

## Remoção do botão Pausar fotos — 11/11/2026

- Removidos botão e estado de pausa em VivaPhotos.jsx, além dos estilos exclusivos do botão. Rotação automática a cada nove segundos e créditos preservados.
- Ajustados os testes para o comportamento solicitado sem pausa manual. Oito testes de home/fotos e build aprovados.
- Conferência visual permanece pendente por navegador indisponível. Próximo passo: conferir o card no portal.

## Páginas das modalidades no modelo Futebol — 11/11/2026

- As 18 páginas novas passaram do modelo simples para a composição de futebol.html: destaque azul com título, conteúdo em duas colunas no desktop, galeria oficial, núcleos publicados e painel lateral de participação/agenda.
- Galerias conectadas ao mecanismo existente /api/fotos pela categoria específica; quando o acervo não possui fotos da modalidade, mantém mensagem informativa, sem fotos de outras práticas.
- Mantidos os 22 links distintos. Futebol, Vôlei e Pilates já usam a organização de referência e foram preservados; Judô recebeu destaque azul compatível, preservando suas seções e galeria.
- Informações específicas existentes e fontes preservadas. Para modalidades não suportadas pela ficha local, participação encaminha à fonte oficial, sem inventar matrícula, horários ou vagas.
- create-school-pages.mjs aceita --refresh somente para páginas com o CSS deste modelo, preservando páginas legadas. Modelo e estilos em public/css/modalidade-escola.css atualizados.
- Verificações: build e 85 testes aprovados; 22 destinos com HTTP 200 e presentes no dist. Teste das novas páginas confere galeria, painel lateral e agenda.
- Limitação: apresentação em navegador não conferida por indisponibilidade na sessão. Próximo passo: conferir visualmente as páginas e detalhar conteúdo conforme dados oficiais disponíveis.

## Menu Modalidades ampliado — 11/11/2026

- Adicionados Futsal e Basquete ao menu compartilhado, com links às páginas específicas e descrições no padrão existente.
- Adicionada opção Ver todas ao final do menu, direcionando a /#escola na página principal, onde estão as 22 modalidades.
- Verificações: cinco testes do cabeçalho aprovados, incluindo novos destinos e ordem de Ver todas; build aprovado.
- Conferência visual pendente por navegador indisponível. Próximo passo: conferir o menu nas páginas do portal.

## Consultar participação em nova aba — 11/11/2026

- Os 16 links Consultar participação das páginas dedicadas agora usam target=_blank e rel=noopener noreferrer, mantendo o destino oficial e a página da modalidade aberta.
- Modelo create-school-pages.mjs atualizado para preservar a regra nas próximas gerações; links Consultar a equipe e Ver agenda não alterados.
- Verificações: sintaxe do gerador, teste dos 22 destinos e build aprovados. Conferência visual pendente por navegador indisponível.
- Próximo passo: conferir a abertura da fonte oficial pelo botão de participação.

## Modalidades migradas para React e URLs sem extensão — 11/11/2026

- As 22 páginas são renderizadas por src/modalidades/ModalidadePage.jsx, usando catálogo e rotas em src/modalidades/. Cards, menu e links internos das modalidades agora usam caminhos sem .html, incluindo /futebol, /volei, /judo, /futsal e /basquete.
- Conteúdo existente convertido em árvore declarativa content.json; sem iframe, HTML injetado ou execução de scripts legados. HTMLs de origem preservados como fonte editorial na transição; script build-modality-react.mjs integrado ao predev/prebuild.
- Galerias/fotos oficiais usam componentes React. Mapa do Futebol preserva os oito núcleos e links de mapas, com ciclo de vida React. Inscrição e participação em nova aba preservadas.
- Middleware Vite e preparação de rotas no build suportam acesso direto e links antigos; Nginx configurado com redirecionamentos de 22 URLs .html e resolução das entradas React. Demais rotas mantidas.
- Verificações: build aprovado; 89 testes aprovados, incluindo renderização das 22 páginas, normalização de URL com parâmetros/fragmentos e mapa. HTTP 200 das 22 rotas e redirecionamento dos 22 endereços antigos verificados em Vite 5174 e preview 5175.
- Corrigido retorno indevido do hook de configuração do middleware, identificado ao iniciar Vite; servidor iniciado e rotas verificadas após a correção.
- Limitações: Nginx não executado; aparência, foco e navegação em navegador permanecem sem conferência visual por navegador indisponível.
- Documentação: docs/modalidades-react.md. Próximo passo: conferir visualmente as páginas React e navegação; outros lotes aguardam orientação.

## Libras no portal existente — 11/11/2026

- Integrado VLibras pelo cabeçalho compartilhado, abrangendo home, 22 modalidades React e páginas internas existentes. Não foi alterado o piloto.
- Carregador assíncrono public/js/portal-libras.js adiciona uma única instância do script oficial https://vlibras.gov.br/app/vlibras-plugin.js. Integração conforme documentação atual do Widget 7, com inicialização automática, sem construtor duplicado.
- Foco do botão configurado pelas variáveis oficiais; falha de carregamento gera aviso acessível na barra sem bloquear o portal.
- Verificações: 92 testes aprovados e build aprovado. Testes cobrem carregamento único, falha e integração pelo shell compartilhado.
- Referência: https://vlibras.gov.br/doc/widget/installation/webpageintegration.html . Botão flutuante disponibiliza tradução automática para Libras pelo serviço oficial.
- Limitação: abertura do avatar, tradução e operação real do serviço ainda precisam de validação visual por navegador indisponível nesta sessão.
- Próximo passo: conferir o botão de Libras no portal e testar a tradução com o usuário.

## Opção Ver todas simplificada — 11/11/2026

- Ver todas no menu Modalidades agora permanece como texto simples em azul #0072e7, sem descrição adicional. Destino /#escola preservado.
- Alterados portal-header.js e portal-base.css. Cinco testes do cabeçalho e build aprovados; conferência visual pendente.

## Esporte nas Regionais — página do card Cobertura — 11/11/2026

- Implementada /esporte-nas-regionais em React no portal existente e vinculada ao card Cobertura. Cabeçalho, rodapé, VLibras e identidade preservados; nenhum site paralelo criado.
- Referência oficial consultada nas dez regionais. Importados 30 equipamentos, duas entradas de programas/entidades e 20 fotos reais. Fotos de exemplo excluídas; imagens reduzidas de cerca de 37 MB para 1,4 MB.
- Navegação por dez cards, seleção com URL, cinco filtros combináveis, cards de equipamentos, detalhes em modal, Google Maps por endereço, seção regional, programas, eventos cadastrados e galeria. Estados de carregamento, erro, ausência de resultados e campos pendentes contemplados.
- Sem GeoJSON ou coordenadas oficiais acessíveis: utilizada a alternativa por cards expressamente prevista no prompt, sem fronteiras ou localizações estimadas. Componente de mapa preparado para pontos com coordenadas confirmadas.
- Nova API GET /api/regionais/catalogo pública e POST restrita a ADMIN. Painel de edição JSON disponível na página somente para sessão administrativa. Validação de fontes/vínculos/imagens/coordenadas e persistência em arquivo atômico, sem alterar esquema do banco existente.
- Backend local reiniciado para ativar a rota. Verificações: 96 testes frontend, 78 backend e build aprovados; HTTP 200 da página/API e 20 imagens; POST sem login retornou 401.
- Limitações: endereços incompletos, horários, acessibilidade, fotos e eventos não confirmados continuam ausentes ou sinalizados. Algumas classificações territoriais da fonte merecem revisão municipal, sem correção por inferência. Conferência visual de filtros, modal e responsividade não executada por navegador indisponível.
- Detalhes de fontes, importação, atualização administrativa e critérios em docs/esporte-nas-regionais.md.
- Próximo passo: conferir visualmente com o usuário e obter GeoJSON/coords oficiais para ampliar o mapa, mediante novo lote autorizado.

## Título do card Núcleos — 11/11/2026

- Renomeado Cobertura para Núcleos, incluindo nome acessível. Valor 10 Regionais e destino /esporte-nas-regionais preservados.

- Verificação: nove testes de home/regionais aprovados. Alteração textual; conferência visual pendente.

## Publicação da versão 1.2 — 11/11/2026

- Publicação solicitada explicitamente pelo usuário no GitHub guilhermetorres02004-spec/Esportes, branch master, com tag v1.2 e Release correspondente.
- Escopo preparado: portal existente, APIs, dados/fotos oficiais e documentação. Piloto antigo, vendor do piloto, configuração Yarn desse piloto, ajustes locais da IDE e hooks de modernização preservados fora do envio.
- Verificações finais repetidas: build aprovado; 96 testes frontend e 78 backend aprovados. Arquivos preparados conferidos sem tokens/chaves privadas detectados e diff sem erros de whitespace.
- Notas da versão em docs/releases/v1.2.md. Pendências visuais e de dados geográficos permanecem documentadas, sem bloqueio da publicação do código solicitado.
- Publicação confirmada: commit f02aa2170acdee1a8b0cdb67278ba4de1afaa5e0 enviado à master e tag anotada v1.2; Release pública criada em https://github.com/guilhermetorres02004-spec/Esportes/releases/tag/v1.2 . Referências remotas conferidas após envio.

## Pequenos saltos nos ícones da home — 11/11/2026

- Ícones circulares dos seis indicadores da home receberam dois saltos discretos (5px e 2px) em ciclo de 3,6 segundos, com início escalonado entre os cards.
- Centralização dos círculos preservada; animação desativada para prefers-reduced-motion. Alteração somente em home.css, sem envio adicional ao GitHub solicitado.
- Verificação: build aprovado. Conferência visual permanece pendente por navegador indisponível.
- Próximo passo: conferir a intensidade dos saltos no portal.

## Núcleos com Google Maps e seleção prévia — 11/11/2026

- Entrada da página agora mostra Google Maps de Betim e botões das dez regionais; informações, filtros, equipamentos, programas, eventos e fotos somente após seleção válida. Limpar filtros mantém a regional; Escolher outra regional retorna ao estado inicial.
- Mapa integrado consulta o endereço publicado da administração regional selecionada, sem coordenadas inferidas ou fronteiras desenhadas. Referências em src/regionais/mapas-regionais.js; cada equipamento preserva seu próprio Como chegar.
- Google Maps incorporado verificado por HTTP 200 e redirecionamento ao endpoint /maps/embed. Botão de abertura externa e aviso de falha disponíveis. GeoJSON municipal consultado continua indisponível.
- Verificações: cinco testes específicos e build aprovados; suíte completa final com 97 testes aprovados. Renders verificam ausência de dados antes da seleção e dados exclusivos do Centro após selecionar; endereços das dez regionais testados nas consultas do Maps.
- Limitação: interação real com o iframe e conferência visual pendentes por navegador indisponível. Alterações locais, sem novo envio ao GitHub solicitado.
- Próximo passo: conferir o mapa e seleção no portal com o usuário.

## Dez pins pré-definidos nas regionais — 11/11/2026

- Usuário escolheu explicitamente usar o mapa interativo do portal com links para Google Maps. RegionalMap usa Leaflet existente e mostra dez pins desde a abertura.
- Pins nomeados e acessíveis por teclado; clique escolhe a regional, destaca o pin em amarelo e revela as informações, preservando a regra de seleção prévia. Botões regionais seguem como alternativa.
- Pontos das dez relações administrativas do OpenStreetMap confirmados via Nominatim em consultas pontuais, identificadas, limitadas e cacheadas. Não são sedes exatas nem fronteiras oficiais; precisão explicitada na interface. Centro usa a referência Sede do OSM. Tentativas de endereço ambíguas não foram usadas como posição.
- Dados e proveniência em src/regionais/pins-regionais.json; respostas de pesquisa em docs/regionais-geocodificacao.json. Nenhuma consulta de geocodificação é feita a cada visita.
- Verificações: oito testes específicos de pins/regionais e build aprovados; suíte frontend completa com 100 testes aprovados. Mocks verificam dez marcadores, IDs de clique, destaque e desmontagem.
- Limitação: aparência dos pins e navegação real em navegador ainda pendentes por navegador indisponível. Alterações locais, sem novo envio ao GitHub solicitado.
- Próximo passo: conferir pins e seleção no mapa com o usuário.
## Zoom e pins dos equipamentos — 11/11/2026

- Selecionar uma regional aproxima o mapa e mostra seus equipamentos com posição confirmada. Clicar no equipamento amplia até zoom 16 e abre balão com foto (quando disponível), nome, categoria, regional/bairro, descrição e botão Conhecer espaço, ligado ao modal existente.
- Pins com cores e ícones por categoria: campo, quadra, praça/parque, complexo e pista. Legenda sobre o mapa, com adaptação para telas pequenas.
- Nove dos 30 equipamentos receberam referências verificadas no OpenStreetMap; os outros 21 continuam na lista e são sinalizados como pendentes de coordenadas, sem posições inventadas. Proveniência e precisão em frontend/src/regionais/equipamentos-pins.json; pesquisa cacheada em docs/equipamentos-geocodificacao.json. Resultados ambíguos foram descartados; consultas complementares ao Overpass falharam por indisponibilidade.
- Verificação final: 102 testes frontend e build aprovados. Testes cobrem classificação, ausência de coordenadas, zoom, conteúdo do balão e abertura dos detalhes. Conferência visual e interação real continuam pendentes por navegador indisponível nesta sessão.
- Alterações locais, sem novo envio ao GitHub. Próximo passo: conferir visualmente e obter coordenadas confirmadas para os 21 locais restantes.

## Cliques sem zoom automático — 11/11/2026

- Conforme nova orientação, selecionar regionais ou clicar em equipamentos preserva o zoom atual. Removidos os reenquadramentos automáticos após a seleção; o enquadramento inicial das dez regionais permanece.
- Balões, pins e abertura dos detalhes preservados. Texto de orientação atualizado.
- Verificação: dez testes de pins/regionais aprovados, incluindo ausência de setView e de fitBounds adicional ao selecionar ou abrir um local. Conferência visual pendente por navegador indisponível.
## Espaçamento dos nomes no mapa — 11/11/2026

- Ampliada a altura do mapa no desktop de 480 para 640px e permitido enquadramento inicial até zoom 13, preservando todos os pontos no enquadramento.
- Rótulos de Alterosas, Imbiruçu, Teresópolis e PTB distribuídos nas laterais dos pins para reduzir a sobreposição no trecho central. Cliques continuam sem alterar o zoom.
- Verificação: dez testes de pins/regionais aprovados. Ajuste visual ainda requer conferência no navegador, indisponível nesta sessão.

## Mapa fixo sem navegação manual — 11/11/2026

- Desativados arraste, gestos de zoom, duplo clique, seleção de área, navegação pelo teclado e controles de zoom. Pins continuam acessíveis por teclado e clicáveis.
- Desativado deslocamento automático ao focar pins ou abrir balões, preservando o enquadramento inicial.
- Verificação: testes de pins/regionais executados após alteração; conferência visual no navegador permanece pendente.

## Balão legível e fechamento no segundo clique — 11/11/2026

- Balão dos equipamentos reposicionado no topo central do mapa, com largura responsiva, altura limitada e rolagem interna para evitar corte da foto e dos textos nas bordas. Fundo branco, contraste explícito e quebra de títulos longos.
- Removido o handler que reabria o balão após o fechamento nativo do Leaflet: segundo clique no mesmo ícone agora alterna o fechamento. Mapa continua fixo.
- Verificação: dez testes de pins/regionais aprovados, incluindo segundo clique. Conferência visual no navegador permanece pendente nesta sessão.

## Página de conquistas em React — 11/11/2026

- Criada ConquistasPage em React na rota /conquistas, preservando apresentação, nove perfis, fontes, linhas do tempo em diálogo nativo, foco e bloqueio de rolagem durante o perfil.
- Consulta da API /api/conquistas migrada para effect com cancelamento e limpeza de timer/listener. Mantidos intervalo de um minuto, pausa em aba oculta, validação, fallback local e seleção de até seis destaques por ano com regras existentes.
- Cards iniciais extraídos do HTML existente para src/conquistas/cards.json; script scripts/extract-conquistas.mjs permite repetir a extração. HTML original e scripts legados preservados como referência, sem execução na rota React.
- Link da home usa /conquistas. Desenvolvimento/preview e build atendem rota limpa; conquistas.html redireciona mantendo consulta e fragmento no build. Cabeçalho compartilhado e acessibilidade permanecem na entrada do portal.
- Verificação: build e 104 testes frontend aprovados, incluindo renderização dos nove perfis e entradas publicadas. Conferência visual e operação real dos diálogos em navegador permanecem pendentes nesta sessão. Sem novo envio ao GitHub.

## Imagem do troféu em conquistas — 11/11/2026

- Imagem enviada pelo usuário adicionada ao destaque da página React /conquistas, substituindo a foto anterior de atletismo. Mantida proporção original e descrição acessível; retirada legenda que atribuía a imagem anterior à Prefeitura.
- Arquivo local em frontend/public/Imagens/conquistas/trofeu-destaque-esportivo-betim.png. Verificação de tamanho da cópia: 2.229.316 bytes; testes React de conquistas executados após alteração.

## Padronização visual de conquistas — 11/11/2026

- Aplicados tokens existentes de largura, superfícies, bordas, raios e sombras na página React. Destaque azul institucional com troféu preservado, tipografia Montserrat/Karla e navegação por anos com alvos de pelo menos 44px.
- Cards com bordas discretas, títulos azuis, textos ajustáveis e botões azuis. Diálogo com linha do tempo azul, foco visível, fechamento de 44px e disposição em uma coluna para datas/textos em telas pequenas.
- Incluído rodapé institucional compartilhado. Estilos específicos em frontend/src/conquistas/conquistas.css; API, nove perfis, seleção por relevância e rotas preservados.
- Verificação: build e 104 testes frontend aprovados. Conferência visual/responsiva no navegador permanece pendente por indisponibilidade nesta sessão. Alterações locais, sem publicação.

## Atalho :skill com padrões e React para as IAs — 11/11/2026

- Criada referência comum docs/padroes-portal.md com identidade visual, componentes compartilhados, acessibilidade, rotas React e critérios de continuidade.
- AGENTS.md e a skill local decompor-tarefas agora incluem essa referência ao ativar :skill. Entradas de Copilot, Claude, Gemini e Cursor apontam para as mesmas regras; Codex segue AGENTS.md.
- Registrado o estado real: home, 22 modalidades, Núcleos e Conquistas já em React. Páginas legadas devem ser adaptadas quando estiverem no escopo, sem declarar migração global concluída nem recriar o portal.
- Alteração somente de instruções; não modifica páginas ou instala extensões. Aplicação depende de cada ferramenta carregar suas instruções locais. Verificados caminhos de referência e consistência das cinco entradas.

## Atalho :commit com versão sequencial — 11/11/2026

- Definido em AGENTS.md e referenciado nas entradas de Copilot, Claude, Gemini e Cursor. Invocar :commit autoriza commit local e tag anotada v1.N, incrementando o inteiro da maior tag da série, com revisão, verificações e notas de versão.
- Tags locais verificadas neste momento: v1.1 e v1.2; a próxima local seria v1.3, sujeita à consulta remota ao executar o atalho. Sequência numérica prevê v1.9 → v1.10.
- Sem alterações, não cria versão vazia. Não inclui automaticamente arquivos locais excluídos anteriormente, segredos ou dados operacionais. Push/Release/deploy exigem pedido adicional.
- Verificação documental: referências do atalho conferidas nas cinco entradas; nenhum commit, tag ou envio executado durante a criação do comando.

## Skills externas para atalhos — 11/11/2026

- Criadas três skills independentes em `C:\Users\walter\Desktop\skills`: `decompor-tarefas` (`:skill`), `retomada` (`:return`) e `commit` (`:commit`).
- Cada pasta contém um `SKILL.md` com frontmatter e instruções adaptadas das regras vigentes; os arquivos de instrução originais não foram alterados nesta tarefa.
- Conferidos os três arquivos, nomes, frontmatter e regras essenciais. A instalação/configuração para descoberta automática pela ferramenta não foi solicitada nem realizada.

## Limpeza de logs de testes — 11/11/2026

- Removidos da raiz 15 logs temporários com nomes `*-tests.log`: saídas de execuções Node.js e Maven sem referências no código/documentação e já ignoradas pela regra `*.log`.
- Não foram removidos logs de runtime. `regionais-runtime.log` e `viva-api-runtime.log` permaneceram presentes na verificação final.
- Verificação: nenhum arquivo `*tests.log` restou na raiz. Os logs foram descartados como saídas reproduzíveis de testes; não houve alteração de código nem execução de testes.

## Topo da página ao recarregar — 11/11/2026

- Em `frontend/public/js/section-navigation.js`, uma recarga manual agora desativa a restauração automática do scroll até o evento `load`, força a página ao topo e depois restaura a preferência anterior do navegador.
- O fragmento não reposiciona a página durante essa recarga; depois de `load`, os eventos de fragmento voltam a navegar normalmente. Navegações sem recarga preservam o comportamento anterior.
- Testes: `node --test tests/section-navigation.test.mjs` (2/2); suíte frontend (`node --test tests/*.test.mjs`, 106/106); `npm run build` concluído. Desenvolvimento serviu `/` e `/js/section-navigation.js` com HTTP 200.
- Próximo passo deste ajuste: confirmar no navegador que uma recarga manual feita após rolar a página retorna ao topo; a sessão não forneceu ferramenta de interação com navegador.

## Entrada de usuário no cabeçalho — 11/11/2026

- Ativado o acesso existente `login.html` para visitantes e apresentado como botão “Entrar”, com ícone decorativo e cápsula branca no cabeçalho azul institucional.
- A área de inscrição segue desativada; autenticação, verificação de sessão, saída e ocultação do acesso para usuário autenticado permanecem no fluxo existente.
- Verificações: testes de cabeçalho e sessão 14/14; suíte frontend 107/107; build de produção concluído; `/js/portal-header.js`, `/css/portal-base.css` e `/login.html` responderam HTTP 200 no servidor local.
- Conferência visual interativa em navegador não realizada nesta sessão.

## Posicionamento do botão Entrar — 11/11/2026

- Movido o botão “Entrar” da navegação azul para a faixa superior, à direita do título “Portal do Esporte”, preservando o mesmo link, identificador e controle de sessão.
- A cápsula branca mantém alvo mínimo de 44px e agora tem foco visível contrastante; em telas menores fica no canto superior direito, sem alterar a lógica de inscrição ou autenticação.
- Verificações: testes de cabeçalho e sessão 14/14; suíte frontend 107/107; `npm run build` concluído. A conferência visual interativa não foi realizada nesta sessão.
## Login React e conferência das skills exportadas — 11/11/2026

- Página de login migrada para React em /entrar, com estilos do portal: azul institucional, Montserrat/Karla, raios e sombras compartilhados, foto local e rodapé institucional. Formulário responsivo com labels, autocomplete, foco no erro, indicação de envio e bloqueio de duplicação.
- Preservados POST /login, conteúdo form-urlencoded, cookie de sessão e destinos administrativos existentes. Destino proxima aceita somente agendamento-quadras.html; demais valores seguem para administrativo.html. Requisição tem timeout e cancelamento ao desmontar. Sem armazenamento de senhas no navegador.
- login.html redireciona para /entrar preservando query/fragmento no build; desenvolvimento/preview atendem ambas as entradas. Endpoint /login e backend preservados. HTML original permanece como referência.
- Build e 111 testes frontend aprovados. HTTP 200 de /entrar e redirecionamento do login antigo com proxima confirmados. Login real com credenciais e conferência visual não executados nesta sessão.
- Conferida a pasta C:/Users/walter/Desktop/skills: já contém decompor-tarefas, retomada e commit, cada uma com SKILL.md e regras correspondentes aos atalhos. Arquivos existentes preservados; diretório externo não é incluído automaticamente no Git nem instalado automaticamente nas ferramentas.
- Sem commit ou publicação. Próximo passo: conferir visualmente o login e validar acesso em ambiente autorizado com credenciais do usuário.

## Marcas no cabeçalho fixo ao rolar — 11/11/2026

- Adicionadas marcas compactas na navegação compartilhada: Prefeitura à esquerda dos menus; Fundação Beta imediatamente à esquerda de Portais. Permanecem ocultas enquanto a faixa superior das marcas está visível.
- Script portal-header-scroll.js observa a saída da faixa superior com IntersectionObserver, com alternativa de scroll passivo; atualiza também no resize e pageshow. Voltar ao topo oculta as marcas compactas. Links ocultos ficam fora da navegação por teclado.
- Entrada com deslocamento de 8px e opacidade em 280ms; prefers-reduced-motion remove a animação. Em telas menores, menus ficam em linha própria para acomodar as marcas.
- Oito testes específicos de cabeçalho aprovados, incluindo saída/retorno da faixa e alternativa sem IntersectionObserver. Conferência visual no navegador permanece pendente nesta sessão. Sem publicação.

## Atalho :adpte para padronização e React — 11/11/2026

- Criada .agents/skills/adpte/SKILL.md e registrada em AGENTS.md, Copilot, Claude, Gemini e Cursor. Uso: :adpte seguido de página, arquivo ou rota; adapta aos padrões e migra para React, ou reutiliza o componente já migrado.
- Sem página explícita, usa somente destino inequívoco da tarefa ativa; caso contrário solicita o destino. Preserva conteúdo, APIs, sessão e compatibilidade das rotas, verifica a entrega e atualiza o registro. Não autoriza commit/publicação.
- Cópia adicionada a C:/Users/walter/Desktop/skills/adpte/SKILL.md, com hash idêntico ao arquivo local. Demais skills externas preservadas.
- Verificadas referências nas cinco entradas, metadados e caminhos da skill. Alteração somente de instruções; nenhuma página migrada nesta tarefa.

## Administrativo adaptado para React — 11/11/2026

- Pedido :adapte administrativo.html interpretado como invocação da skill adpte. Criada rota /administrativo com componentes AdminPage, AdminContent e AdminDocument, no frontend existente.
- Preservados sete cards de gestão, 12 opções de documentos, resumos/PDFs/fontes/ações, acesso ao agendamento, estrutura, quadro de cargos e valores da folha já existentes. Conteúdo migrado de forma declarativa pelo script scripts/extract-admin-react.mjs; scripts legados não são executados na rota React.
- Mantida guarda baseada em sessão válida de /api/auth/me com cookie, sem cache, timeout e limpeza ao desmontar. Não renderiza painel antes da validação; falha redireciona a /entrar. Revalida ao retornar à aba/página. Permissões e autenticação do backend preservadas.
- Aplicados identidade azul, tipografia e tokens do portal, cards de 16px, foco visível, botões de 44px, tabelas com rolagem horizontal quando necessário, diálogo nativo com retorno de foco e rodapé compartilhado.
- administrativo.html redireciona para a rota limpa em desenvolvimento/preview e build. Query e fragmento preservados pelo redirecionamento da saída; HTML original mantido como referência.
- Verificação: build e 118 testes frontend aprovados; testes novos cobrem sessão inválida, conteúdo oculto antes de autenticar, IDs dos documentos, tabelas, PDFs/resumos e entradas publicadas. HTTP 200 de /administrativo e redirecionamento legado com query confirmados.
- Limitação: acesso real com credenciais e conferência visual/responsiva do painel e diálogos não executados por navegador indisponível. Sem commit, publicação ou alteração de banco.

## Login retorna à home e libera Eventos/Gestão — 11/11/2026

- Após autenticar em /entrar, destino agora é sempre /, inclusive quando há proxima na URL. Substitui a orientação anterior de redirecionar ao administrativo/agendamento.
- Restaurados os dois menus antigos identificados no histórico: Eventos (agenda, detalhes e Horto) e Gestão (Administrativo, Arquivos, Downloads, Almoxarifado, Presença, Matrix Impact e Página Base). Usuário confirmou Gestão e o outro menu.
- Menus iniciam ocultos e são exibidos somente após sessão válida de /api/auth/me. Logout e sessão expirada ocultam e fecham os menus. Permissões das APIs permanecem no backend; exibir links não altera autorizações.
- Ajustada quebra da navegação autenticada nas larguras intermediárias para acomodar os itens. Mantidos cabeçalho azul, marcas no scroll e menus por hover/teclado.
- Verificação: build e 119 testes frontend aprovados, incluindo destinos, menus inicialmente ocultos, exibição autenticada, expiração e logout. Conferência visual e login com credenciais reais continuam pendentes nesta sessão. Sem publicação.

## Botão Sair no lugar de Entrar — 11/11/2026

- Movido o único botão Sair da navegação para a faixa superior, na mesma posição de Entrar. Reutiliza a classe da cápsula branca, alinhamento e regra responsiva; largura mínima de 110px em ambos mantém dimensões coerentes.
- Alternância segue a sessão existente: visitante vê Entrar, autenticado vê Sair. Preservados POST /logout, bloqueio durante envio e mensagens de falha; ícone de saída decorativo adicionado.
- Verificação de estrutura cobre Sair somente na faixa superior e ausência de IDs duplicados. Conferência visual no navegador permanece pendente.

## Inscrição no menu Gestão — 11/11/2026

- Adicionada opção Inscrição no menu Gestão, após Administrativo, apontando para /Inscricao.html existente. Mantida visibilidade do menu apenas com sessão válida; botão público Inscreva-se continua oculto.
- Verificação: testes de cabeçalho e sessão executados após a alteração.

## Inscrição adaptada para React — 11/11/2026

- Pedido :adapte inscricao.html aplicado com a skill adpte. Criados InscricaoPage, ImageConsent e TermText na rota /inscricao, com fontes, tokens, cards, botões azuis, labels associados, fieldsets e rodapé compartilhado. Bootstrap e scripts legados não são executados nessa rota.
- Preservados os 20 campos, cinco seções, quatro modalidades existentes e seus núcleos. Contexto por query modalidade e retorno para a modalidade mantidos. Conteúdo extraído de Inscricao.html por scripts/extract-inscricao-react.mjs; dados em src/inscricao/form-data.json.
- Preservadas máscara e validação dos CPFs opcionais do atleta/responsável. O termo de imagem mantém texto e versão 2026-11-04, sem autorização pré-marcada: sim=true, não=false, ausência=null. Retorno de foco, bloqueio de fechamento durante envio e recuperação de rolagem implementados no diálogo nativo.
- POST /api/inscricoes continua com JSON, cookie e dadosFicha completos. Confirma protocolo antes de limpar o formulário; erro mantém os dados e escolha. Bloqueio de duplicação, timeout de 20s e cancelamento ao desmontar; nenhum dado pessoal é salvo no armazenamento do navegador. Backend preservado.
- Menu Gestão aponta à rota limpa. Inscricao.html/inscricao.html continuam compatíveis em desenvolvimento/preview e build, mantendo modalidade e fragmento no redirecionamento.
- Verificação: build e 125 testes frontend aprovados. Novos testes cobrem CPF, três estados de autorização, payload, erros, protocolo, 20 campos, contextos e fluxo React com bloqueio de envio duplicado. HTTP 200 de /inscricao e redirecionamento antigo com modalidade confirmados.
- Limitação: conferência visual/responsiva e envio real com dados de inscrição não executados nesta sessão; não foram criados registros de teste no banco operacional. Sem commit/publicação.

## Inscrição conforme planilha Viva Esportes — 11/11/2026

- Página /inscricao reorganizada conforme Ficha_Inscricao_Programa_Viva_Esportes.xlsx fornecida pelo usuário, aba Ficha de Inscrição, A1:D40. Planilha original não modificada.
- Seis blocos preservados: participante, responsável, atividade, saúde/segurança, declaração e uso exclusivo da Secretaria. Agora 26 controles incluem aceite da declaração; adicionados e-mail, parentesco, telefones, turno, turma, emergência, condição/adaptação, data e nome de quem assina. Campos antigos ausentes no modelo foram substituídos.
- Responsável exigido quando menor de 18 anos; sexo sem seleção obrigatória; turnos e resposta de saúde seguem listas da planilha. Modalidade/núcleo aceitam texto livre com sugestões existentes, sem limitar Viva Esportes às quatro modalidades anteriores.
- Declaração de participação separada do uso de imagem, que continua opcional e sem seleção inicial. Área da Secretaria somente leitura: protocolo/data confirmados pela API; situação/servidor não inferidos. Não implementado editor administrativo desses campos.
- API e banco preservados. Campos completos em dadosFicha; e-mail também no campo próprio já suportado. Sem persistência de dados pessoais no navegador.
- Referência, mapeamento de células e adaptações em docs/ficha-viva-esportes.md. Script build-viva-form.mjs mantém a estrutura; extrator HTML antigo não sobrescreve a nova ficha.
- Verificação: build e 127 testes frontend aprovados. HTTP 200 da rota. Conferência visual e envio real continuam pendentes; nenhum registro inserido no banco operacional. Sem commit/publicação.

## Aplicação de :adpte na ficha Viva Esportes — 11/11/2026

- Revisada apresentação da página já em React: destaque azul institucional, título em duas linhas, etiqueta do portal e instruções com contraste sobre o fundo azul.
- Seções com numeração circular azul e títulos acessíveis; campos em duas colunas no desktop e uma no celular, com nomes/endereço/assinatura em largura ampliada. Inputs usam superfície branca e foco azul conforme padrão do portal.
- Ações finais em painel compartilhando bordas, raios e sombra dos cards; área exclusiva da Secretaria destacada com fundo suave e borda tracejada. Mantidos todos os 26 controles, seis blocos, consentimento e API.
- Verificação: build e 15 testes de inscrição aprovados. Conferência visual no navegador permanece pendente nesta sessão. Sem publicação.

## Preparação da versão 1.3 — 11/11/2026

- Atalho :commit solicitado. Tags locais e origin consultadas: maior v1.2; próxima versão v1.3. Branch master, sem arquivos staged anteriores à preparação.
- Escopo: mudanças do portal desde v1.2, componentes React, inscrição Viva Esportes, mapas, cabeçalho/sessão, imagens, testes e instruções. Excluídos ajustes .vscode, frontend/piloto, frontend/vendor e frontend/.yarnrc.yml, conforme orientação vigente.
- Verificação final repetida: build de produção e 127 testes frontend aprovados. Notas em docs/releases/v1.3.md, incluindo limitações visuais, dados geográficos pendentes e arquivos externos não incluídos.
- Preparar commit release: v1.3 e tag anotada local. Sem autorização de push/publicação neste pedido.

## Inicialização local — 12/11/2026

- Frontend iniciado com npm run dev -- --host 127.0.0.1 --port 5174; GET / respondeu HTTP 200.
- API não iniciada: projeto exige Java 25; Java padrão disponível é 8 e instalação adicional encontrada é Java 21. Java 25 não localizado nos diretórios consultados.
- Nenhuma alteração funcional ou instalação realizada. Próximo passo: disponibilizar/localizar Java 25 e iniciar backend/mvnw.cmd spring-boot:run. Login e funções dependentes da API não verificados.


## Botão Entrar conforme referência — 12/11/2026

- Ajustado o acesso compartilhado existente: cápsula branca de 134px mínimos por 56px, ícone de entrada, texto azul institucional e tipografia do portal; hover e foco preservados.
- Link aponta diretamente para /entrar (React existente). Visível desde a montagem, inclusive com API indisponível; sessão válida continua ocultando Entrar e exibindo Sair. Nenhuma permissão de API alterada.
- Verificações: 15 testes de cabeçalho/sessão aprovados e build de produção aprovado. Conferência visual interativa não realizada. Login real segue dependente da API/Java 25.
- Sem commit ou publicação. Próximo passo: conferir visualmente o botão no portal local.


## Conexão do login restabelecida — 12/11/2026

- Causa confirmada: API parada na porta 8080, produzindo HTTP 500 pelo proxy de login do Vite.
- Java portátil Temurin 25.0.5+8 baixado para backend/.tools/java25 (ignorado pelo Git). backend/mvnw.cmd agora prefere essa instalação local sem alterar o Java global.
- API iniciada com backend/mvnw.cmd spring-boot:run, PID 16092. Maven confirmou Java 25.0.5. GET /api/auth/me direto e via Vite retornou 401; POST /login sem credenciais via Vite retornou 401 esperado, confirmando comunicação com autenticação.
- Quatro testes de login React aprovados. Sem alterações em credenciais ou regras de autenticação. Acesso real do admin não testado com senha do usuário.
- Próximo passo: usuário repetir login em http://127.0.0.1:5174/entrar. Frontend e API precisam permanecer ativos. Sem commit/publicação.


## Botão Entrar menor — 12/11/2026

- Reduzidos mínimos de 134 × 56px para 110 × 44px, fonte de 16px para 14px e espaçamentos internos. Estilo, foco e destino /entrar preservados.
- Sete testes do cabeçalho aprovados; conferência visual pendente. Próximo passo: conferir tamanho no portal local.

## Botão Sair menor — 12/11/2026

- Reduzida largura mínima de 110px para 90px, fonte para 13px e espaçamentos internos; alvo mínimo de 44px de altura preservado. Fluxo de logout mantido.
- Quinze testes de cabeçalho/sessão aprovados. Conferência visual pendente; próximo passo: conferir tamanho no portal local.

## Agenda adaptada para React — 12/11/2026

- Migrada agenda.html para /agenda no frontend existente: AgendaPage, AgendaDialog, EventoForm, serviço e oito eventos iniciais extraídos sem alteração de conteúdo. HTML original preservado como referência, sem executar seus scripts na rota React.
- Aplicados identidade azul, Montserrat/Karla, tokens, cards, botões de 44px, labels, diálogo nativo com Escape/foco e rodapé compartilhado. Calendário em 42 células com rolagem horizontal no celular e estilo de impressão.
- Preservados eventosEsporte e eventoSelecionado no localStorage, detalhes-evento.html, criação/edição/exclusão com confirmação, oito campos, mês atual/futuro, datas de São Paulo e bloqueio dos dias passados. Dados não foram transferidos para API; erros de armazenamento exibem mensagem sem sobrescrever dados inválidos ou descartar formulário.
- Timers e listeners de atualização do dia são removidos ao desmontar. Menu aponta para /agenda; entrada antiga redireciona com query/fragmento em desenvolvimento/preview e build.
- Verificações: build aprovado; 132 testes frontend aprovados, cinco novos da agenda React; GET /agenda HTTP 200 e agenda.html?origem=menu redirecionou para /agenda?origem=menu. Testes legados permanecem como regressão da referência antiga.
- Limitação: navegador de conferência indisponível (nenhum navegador conectado); criação/edição/exclusão via UI, impressão e apresentação responsiva precisam de conferência visual. Nenhum evento de teste gravado no navegador do usuário. Sem commit/publicação.
- Próximo passo: conferir /agenda visualmente e exercitar os diálogos com o usuário; próximo lote geral do rodapé permanece separado.

## Pesquisa de fotos antes/depois das quadras — 12/11/2026

- Usuário confirmou comparação antes/depois da reforma e pediu pesquisa na internet. Consultadas notícias oficiais, imprensa local e busca de imagens.
- Fontes: https://www.betim.mg.gov.br/portal/noticias/0/3/13004/horto-municipal-segue-recebendo-melhorias/ (melhorias de 2024); https://portalagita.com.br/equipamentos-esportivos-sao-revitalizados-pela-prefeitura-de-betim/ (23/06/2020, cobertura das quadras 1 e 2 prevista); https://www.betimonline.com/index.php/noticias/horto-municipal-ganha-novos-equipamentos-esportivos (27/08/2021, cobertura concluída).
- Encontradas fotos gerais do Horto e das quadras cobertas, mas nenhum par antes/depois confirmado para cada quadra numerada. Não atribuir foto genérica ou do Ginásio Divino Braga a quadra específica.
- Comparador ainda não implementado: faltam pares identificados do mesmo espaço. Próximo passo: obter acervo identificado da Secretaria ou fotos fornecidas pelo usuário. Nenhuma imagem baixada/inserida, sem alterações funcionais neste lote.

## Galeria pública do Horto ao selecionar quadra — 12/11/2026

- Usuário autorizou substituir a comparação antes/depois por galeria de vistas gerais do Horto. Clique em qualquer card mantém seleção/agendamento e abre diálogo com duas fotos locais, legenda, fonte e aviso de que não identificam a quadra selecionada.
- Fotos conferidas visualmente: exterior das quadras cobertas (Portal Agita) e área de areia publicada em agosto de 2021 (Prefeitura/Divulgação via Betim Online). URLs das fontes e créditos registrados em frontend/public/js/horto-galeria.js e exibidos na galeria. Arquivos em frontend/public/Imagens/horto; não representam estado atual comprovado nem par antes/depois.
- Navegação anterior/próxima, setas de teclado, Escape nativo, fechar por botão/fundo, bloqueio/restauração de rolagem e retorno de foco ao card recriado. Erro de foto mantém fonte e controles acessíveis. Agendamento, APIs e sessão preservados.
- Verificação: build aprovado e 136 testes frontend aprovados, quatro novos da galeria. Fotos e módulo servidos com HTTP 200 após reiniciar Vite na porta 5174. Navegador interativo indisponível na sessão; verificação visual final em desktop/celular e Escape nativo pendente.
- Próximo passo: conferir a galeria em agendamento-quadras.html com sessão administrativa. Sem commit/publicação.

## Remoção da galeria das quadras — 12/11/2026

- A pedido do usuário, removidos abertura da galeria, aviso nos cards, módulo, CSS, duas fotos baixadas e testes específicos da galeria. Clique volta a somente selecionar a quadra; agendamento preservado.
- Próximo passo: continuar somente mediante novo pedido. Galeria e comparação antes/depois não fazem mais parte da página.
- Verificações da remoção: 17 testes de agenda/quadras aprovados e build de produção aprovado.

## Agendamento de quadras adaptado para React — 12/11/2026

- Pedido :adapte aplicado ao agendamento-quadras.html. Criada rota /agendamento-quadras com HortoPage, hook useHorto, serviço, cards/horários, formulário e tabela separados no frontend existente. HTML e script antigos mantidos como referência, sem execução na rota React.
- Aplicados identidade azul, tokens, Montserrat/Karla, cards de 16px, foco visível, labels, botões de 44px, grade responsiva e rodapé compartilhado. Fotos/galeria continuam removidas; clique somente seleciona a quadra. Contagens de quadras/areia/cobertas derivadas da API.
- Preservados GET /api/auth/me, /api/horto/quadras, /api/horto/esportes, /api/horto/reservas?data e POST /api/horto/reservas com JSON, cookie e campos existentes. Exige ROLE_ADMIN antes de mostrar dados/formulário; 401 redireciona para /entrar e 403 bloqueia acesso. Revalida ao retornar à página/aba e a cada 30s.
- Reutilizadas regras horto-utils: datas de São Paulo, períodos futuros, mínimo de uma hora, término até 24h, conflitos e esportes conforme piso. Nome e esporte só são limpos após sucesso; falha mantém campos e atualiza disponibilidade. Bloqueio síncrono de envio duplicado, timeout, cancelamento ao desmontar e descarte de respostas antigas ao trocar data.
- Menu usa rota limpa; endereço antigo compatível em desenvolvimento/preview/build com query e fragmento. Inventário React atualizado.
- Verificação final: build aprovado; 141 testes frontend aprovados, nove novos cobrindo API/perfis, datas, conflitos, renderização protegida, 24 horários, rotas, envio duplicado, expiração e cancelamento de consultas. GET /agendamento-quadras HTTP 200; agendamento-quadras.html?origem=menu redirecionou para /agendamento-quadras?origem=menu.
- Limitação: nenhum navegador conectado para validação visual/responsiva ou fluxo com credenciais reais. Nenhuma reserva de teste criada no banco operacional. Sem alteração de backend/banco, commit ou publicação.
- Próximo passo: conferir visualmente e validar reserva em ambiente autorizado; próximo lote geral do rodapé permanece separado.

## Detalhes do evento adaptados para React — 12/11/2026

- Pedido datelhes-eventos.html interpretado como detalhes-evento.html, único arquivo correspondente. Criados EventoPage, EventoContent, serviço de leitura e estilos na pasta src/agenda, rota /detalhes-evento no frontend existente.
- Preservada leitura de eventoSelecionado do localStorage, seus oito campos, quatro orientações de participação, Solicitar material para almoxarifado.html, histórico Voltar e Imprimir. Voltar oferece /agenda quando não existe histórico. Nenhuma API ou credencial alterada.
- Aplicados visual azul, tokens, cards, tipografia compartilhada, responsividade, foco visível e Footer. Renderização React escapa conteúdo. Dados ausentes, JSON inválido, data inválida ou armazenamento bloqueado recebem orientação para retornar à agenda, sem sobrescrever dados.
- Agenda e menu apontam à rota limpa; detalhes-evento.html continua compatível em desenvolvimento/preview/build, preservando query e fragmento. HTML antigo mantido somente como referência.
- Verificações: build aprovado e 147 testes frontend aprovados, seis novos cobrindo contrato local, armazenamento inválido/bloqueado, datas bissextas, navegação, conteúdo/material padrão, texto escapado e saídas publicadas. /detalhes-evento HTTP 200; entrada antiga com origem=agenda redirecionou corretamente.
- Limitação: conferência visual/responsiva, impressão física e fluxo interativo no navegador permanecem pendentes (nenhum navegador conectado nesta sessão). Nenhum dado de evento do usuário modificado. Sem commit/publicação.
- Próximo passo: abrir um evento pela agenda e conferir os detalhes visualmente; rodapé geral permanece lote separado.

## Central Arquivos adaptada para React — 12/11/2026

- comunicacao.html migrada para /comunicacao com ArquivosPage/ArquivosContent, FotosStatus, hook e serviço na pasta src/comunicacao. Visual azul, tokens, tipografia compartilhada, três cards responsivos, foco e controles de 44px, rodapé compartilhado. HTML original preservado como referência.
- Mantidos links importar-videos.html e importar-imagens.html. Não foram migradas as páginas de importação nem alterados uploads, APIs ou autenticação. GET /api/fotos/status e /api/auth/me com cookie/no-store; POST /api/fotos/atualizar somente com perfil ROLE_ADMIN confirmado. Resposta 401/403 oculta atualização manual.
- Intervalo exibido deriva de intervaloSegundos da API (evita afirmar uma hora com backend configurado de outra forma). Acervo/última consulta/aviso exibidos; falha preserva último status e oferece nova consulta. Consulta a cada cinco minutos, a cada três segundos durante atualização e ao retornar à aba; timers/listeners/requisições limpos ao desmontar. Bloqueio de envio duplicado.
- Menu usa rota limpa; comunicacao.html redireciona em desenvolvimento/preview/build preservando query/fragmento. Script legado portal-fotos não manipula o DOM da rota React.
- Verificação: build aprovado e 156 testes frontend aprovados, nove novos da central de arquivos cobrindo contratos, permissão, erros, intervalo/data, renderização, rotas, envio duplicado, polling e cancelamento. GET /comunicacao HTTP 200 e entrada antiga com origem=gestao redirecionou corretamente.
- Limitação: validação visual/responsiva e atualização real das fotos oficiais não realizadas; nenhum navegador conectado nesta sessão. Nenhum upload ou atualização real disparado. Sem commit/publicação.
- Próximo passo: conferir a central visualmente e os links de importação; rodapé geral permanece lote separado.

## Preparação de release v1.4 — 12/11/2026

- Consolidado o estado atual do portal em uma preparação de release local: ajustes no cabeçalho compartilhado, compatibilidade de páginas legadas, roteamento do frontend e revisão das regras de acessibilidade/retomada.
- Na base ativa: `frontend/public/js/portal-header.js` ajusta atributos e comportamento do botão Entrar/menús; `frontend/public/css/portal-base.css` reforça consistência visual; `frontend/src/main.jsx` e `frontend/src/home/Home.jsx` mantêm a entrada React alinhada às rotas antigas; `frontend/scripts/copy-html-pages.mjs` preserva compatibilidade dos HTML legados durante o build; `frontend/vite.config.js` recebe ajuste de servidor para o portal local.
- Testes específicos de cabeçalho e regressão foram atualizados em `frontend/tests/portal-header.test.mjs`, mantendo a validação de comportamento sem tocar em fluxos de autenticação reais.
- Atualizado o registro de retomada e a referência de padrões em `docs/retomada.md` e `docs/padroes-portal.md`, sem alterar o conteúdo funcional do backend ou introduzir dados operacionais.
- Verificações: `npm run build` no frontend concluído com sucesso; suíte `node --test tests/*.test.mjs` passou com código 0. A versão está pronta para commit local e tag `v1.4` na branch atual, sem push ou publicação.
- Limitações: validação visual em navegador e revisão de fluxos com credenciais reais continuam pendentes; o cenário local não oferece navegador conectado nesta sessão. Nenhuma alteração de produção foi enviada ao GitHub.


## Resolução da Issue #2 (Correção de build do Vite) — 12/11/2026

- Corrigida a quebra no `npm run build` causada por imports inexistentes em `frontend/src/main.jsx` (`AgendaPage`, `HortoPage`, `EventoPage`, `ArquivosPage`).
- Em `frontend/scripts/copy-html-pages.mjs`, restaurada a cópia direta dos arquivos HTML canônicos e funcionais (`agenda.html`, `agendamento-quadras.html`, `detalhes-evento.html`, `comunicacao.html`) para a pasta de distribuição `dist/`.
- Em `frontend/vite.config.js`, removidas as regras de rewrite em desenvolvimento para as páginas que ainda operam em HTML estático.
- Verificações: `npm run build` concluiu com sucesso (código 0, saída em `dist/`), e todos os testes da suíte (`test:horto`, `test:inscricao`, `test:agenda`, `test:administrativo`, `test:suporte`, `test:sessao`, `test:fotos`) passaram com 100% de aprovação.
- Issue #2 comentada e fechada no GitHub.
- Próximo passo sugerido: avançar para a Issue #3 (remoção das divs fantasmas `frequencia` e `inscricao` no rodapé da Home).


## Resolução da Issue #3 (Limpeza de divs fantasmas na Home) — 12/11/2026

- Removidas de `frontend/src/home/Home.jsx` as tags `<div id="frequencia">` e `<div id="inscricao">`, que adicionavam `min-h-screen py-24` e espaço em branco desnecessário no fim da página inicial.
- A página agora renderiza `<main>` seguido diretamente pelo `<Footer />`.
- Verificações: `node --test tests/home-react.test.mjs` (5 testes aprovados) e `npm run build` aprovado sem erros.
- Issue #3 comentada e fechada no GitHub.
- Próximo passo sugerido: avançar para a Issue #4 (implementação dos dots de navegação no carrossel).


## Resolução da Issue #4 (Marcadores de navegação no Carrossel) — 12/11/2026

- Implementada a renderização dos botões `.dot` no container `.dots-container` de `frontend/src/home/Carousel.jsx`.
- Cada botão possui `role="tab"`, `aria-selected` vinculado ao índice ativo, `aria-label` acessível e handler de clique `onClick={() => setIndice(i)}`.
- Estilizadas as classes `.dots-container`, `.dot` e `.dot.active` em `frontend/src/home/home.css`, com expansão suave para 32px e amarelo institucional no item ativo, além de foco visível (`:focus-visible`).
- Verificações: `node --test tests/home-react.test.mjs` (5 testes aprovados) e `npm run build` aprovado sem erros.
- Issue #4 comentada e fechada no GitHub.
- Próximo passo sugerido: avançar para a Issue #5 (correção das classes dinâmicas do Tailwind em `News.jsx`).


## Resolução da Issue #5 (Classes dinâmicas do Tailwind em News.jsx) — 12/11/2026

- Substituída a interpolação dinâmica de classes ``border-${original.cor}-600`` e ``text-${original.cor}-600`` em `frontend/src/home/News.jsx` por classes literais estáticas (`borderClass` e `textClass`).
- Confirmada a compilação e presença das regras `border-purple-600`, `border-green-600` e `text-purple-600` no arquivo `frontend/public/css/tailwind.css`.
- Verificações: `npm run build:css`, `node --test tests/home-react.test.mjs` (5 testes aprovados) e `npm run build` aprovados sem erros.
- Issue #5 comentada e fechada no GitHub.
- Próximo passo sugerido: avançar para a Issue #6 (ajuste de caminhos relativos de vídeos em `Sections.jsx`).


## Preparação da release v1.5 — 12/11/2026

- Preparadas para a versão as correções do build e das rotas HTML legadas, a remoção de blocos vazios da Home, a navegação acessível do carrossel, as classes Tailwind estáticas das notícias e o painel de acompanhamento das issues.
- Arquivos JSON gerados com alterações apenas de CRLF/LF foram restaurados após comparação semântica confirmar conteúdo idêntico; não serão incluídos como ruído de formatação.
- Verificações: `node --test tests/*.test.mjs` em `frontend` passou com 127/127 testes; `npm run build` concluiu com sucesso (80 módulos); `git diff --check` sem erros. O build emitiu aviso não fatal de `caniuse-lite` desatualizado.
- A tag remota `v1.5` foi consultada e não existia; `master` estava alinhada com `origin/master` antes da preparação. O usuário autorizou criar commit, tag anotada e enviar ambos.
- Próximo passo: revisar a lista final de arquivos, criar o commit e a tag `v1.5`, enviar ao remoto e conferir os refs. Conferência visual em navegador não realizada; as issues #6 e #7 continuam indicadas como pendentes em `docs/progresso-issues.md`.
