import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import { resolveModalidade } from './src/modalidades/routes.js';

function modalityRoutes() {
  const configure = server => { server.middlewares.use((req, res, next) => {
    const url = new URL(req.url, 'http://localhost');
    if (/^\/(?:index - Copia|index - Copia1|teste28(?: \(2\))?)\.html$/i.test(decodeURIComponent(url.pathname))) {
      res.writeHead(302, { Location: '/' + url.search }); res.end(); return;
    }
    if (/^\/inscricao(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/inscricao') { res.writeHead(302, { Location: '/inscricao' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/inscricaoAluno\/?$/i.test(url.pathname)) { req.url = '/index.html' + url.search; next(); return; }
    if (/^\/administrativo(?:\.html|\/)?$/.test(url.pathname)) {
      if (url.pathname !== '/administrativo') { res.writeHead(302, { Location: '/administrativo' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/(?:entrar\/?|login\.html)$/.test(url.pathname)) {
      if (url.pathname !== '/entrar') { res.writeHead(302, { Location: '/entrar' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/conquistas(?:\.html|\/)?$/.test(url.pathname)) {
      if (url.pathname !== '/conquistas') { res.writeHead(302, { Location: '/conquistas' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/almoxarifado(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/almoxarifado') { res.writeHead(302, { Location: '/almoxarifado' + url.search + url.hash }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/agenda(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/agenda') { res.writeHead(302, { Location: '/agenda' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/agendamento-quadras(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/agendamento-quadras') { res.writeHead(302, { Location: '/agendamento-quadras' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/comunicacao(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/comunicacao') { res.writeHead(302, { Location: '/comunicacao' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/detalhes-evento(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/detalhes-evento') { res.writeHead(302, { Location: '/detalhes-evento' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/downloads(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/downloads') { res.writeHead(302, { Location: '/downloads' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/frequenciapreenchimento(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/frequenciapreenchimento') { res.writeHead(302, { Location: '/frequenciapreenchimento' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/importar-imagens(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/importar-imagens') { res.writeHead(302, { Location: '/importar-imagens' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/importar-videos(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/importar-videos') { res.writeHead(302, { Location: '/importar-videos' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/presenca(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/presenca') { res.writeHead(302, { Location: '/presenca' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/esporte\.html$/i.test(url.pathname)) {
      res.writeHead(302, { Location: '/' + url.search }); res.end(); return;
    }
    if (/^\/esporteteste\.html$/i.test(url.pathname)) {
      res.writeHead(302, { Location: '/' + url.search }); res.end(); return;
    }
    if (/^\/capi(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/capi') { res.writeHead(302, { Location: '/capi' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (/^\/descanso(?:\.html|\/)?$/i.test(url.pathname)) {
      if (url.pathname !== '/descanso') { res.writeHead(302, { Location: '/descanso' + url.search }); res.end(); return; }
      req.url = '/index.html' + url.search; next(); return;
    }
    if (url.pathname.replace(/\/$/, '') === '/esporte-nas-regionais') { req.url = '/index.html' + url.search; next(); return; }
    const modalidade = resolveModalidade(url.pathname);
    if (!modalidade) return next();
    if (url.pathname !== modalidade.href) {
      res.writeHead(302, { Location: modalidade.href + url.search }); res.end(); return;
    }
    req.url = '/index.html' + url.search;
    next();
  }); };
  return { name: 'modality-react-routes', configureServer: configure, configurePreviewServer: configure };
}

export default defineConfig({
  plugins: [modalityRoutes(), react()],
  server: {
    proxy: {
      "/api": "http://localhost:8080",
      "^/login$": "http://localhost:8080",
      "^/logout$": "http://localhost:8080",
    },
  },
});
