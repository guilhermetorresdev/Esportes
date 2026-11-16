# Rotas e implementação atual

Esta tabela descreve o estado do portal no código atual. Arquivos `.html` antigos
continuam publicados quando necessários para preservar links existentes, mas não
indicam sozinhos qual componente renderiza a rota canônica.

| Rota canônica | Implementação | Componente React |
|---|---|---|
| `/` | React | `frontend/src/Home.jsx` |
| `/entrar` | React | `frontend/src/login/LoginPage.jsx` |
| `/administrativo` | React | `frontend/src/admin/AdminPage.jsx` |
| `/inscricao` | React | `frontend/src/inscricao/InscricaoPage.jsx` |
| `/almoxarifado` | React | `frontend/src/almoxarifado/AlmoxarifadoPage.jsx` |
| `/agenda` | React | `frontend/src/agenda/AgendaSharedPage.jsx` |
| `/agendamento-quadras` | React | `frontend/src/horto/HortoPage.jsx` |
| `/comunicacao` | React | `frontend/src/comunicacao/ArquivosPage.jsx` |
| `/detalhes-evento` | React | `frontend/src/agenda/EventoSharedPage.jsx` |
| `/esporte-nas-regionais` | React | `frontend/src/regionais/RegionaisPage.jsx` |
| `/conquistas` | React | `frontend/src/conquistas/ConquistasPage.jsx` |
| `/downloads`, `/frequenciapreenchimento`, `/presenca`, `/capi`, `/descanso` | React | componentes em `frontend/src/` |
| modalidades esportivas | React | `frontend/src/modalidades/ModalidadePage.jsx` |

As rotas não listadas acima permanecem legadas. `frontend/src/main.jsx` e
`frontend/vite.config.js` são as fontes de verdade para montagem e compatibilidade
de URLs; `frontend/nginx.conf` reproduz os rewrites necessários em produção.

## Migrações restantes

Não há declaração de migração global concluída. Novas páginas devem ser migradas
individualmente, preservando APIs, autenticação, parâmetros, links `.html` antigos
e o shell compartilhado. O status das issues deve ser conferido no GitHub e em
`docs/retomada.md` antes de ser apresentado como concluído.
