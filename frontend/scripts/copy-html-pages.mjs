import { copyFileSync, existsSync, mkdirSync, readdirSync, writeFileSync } from "node:fs";
import { modalidadesEscola } from '../src/modalidades/catalog.js';
import { extname, join } from "node:path";

const root = process.cwd();
const dist = join(root, "dist");

if (!existsSync(dist)) {
  mkdirSync(dist, { recursive: true });
}
mkdirSync(join(dist, 'entrar'), { recursive: true });
mkdirSync(join(dist, 'inscricao'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'inscricao', 'index.html'));
mkdirSync(join(dist, 'inscricaoAluno'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'inscricaoAluno', 'index.html'));
for (const legacy of ['Inscricao.html', 'inscricao.html']) writeFileSync(join(dist, legacy), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/inscricao"><title>Inscrição</title><a href="/inscricao">Abrir inscrição</a><script>location.replace("/inscricao"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'administrativo'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'administrativo', 'index.html'));
writeFileSync(join(dist, 'administrativo.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/administrativo"><title>Administrativo</title><a href="/administrativo">Abrir administrativo</a><script>location.replace("/administrativo"+location.search+location.hash)</script></html>');
copyFileSync(join(dist, 'index.html'), join(dist, 'entrar', 'index.html'));
writeFileSync(join(dist, 'login.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/entrar"><title>Entrar</title><a href="/entrar">Abrir login</a><script>location.replace("/entrar"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'esporte-nas-regionais'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'esporte-nas-regionais', 'index.html'));
mkdirSync(join(dist, 'conquistas'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'conquistas', 'index.html'));
writeFileSync(join(dist, 'conquistas.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/conquistas"><title>Conquistas</title><a href="/conquistas">Abrir conquistas</a><script>location.replace("/conquistas"+location.search+location.hash)</script></html>');

for (const modalidade of modalidadesEscola) {
  const directory = join(dist, modalidade.href.slice(1));
  mkdirSync(directory, { recursive: true });
  copyFileSync(join(dist, 'index.html'), join(directory, 'index.html'));
  writeFileSync(join(dist, modalidade.legacyHref), `<!doctype html><html lang="pt-BR"><head><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=${modalidade.href}"><title>Redirecionando</title></head><body><a href="${modalidade.href}">Abrir modalidade</a><script>location.replace(${JSON.stringify(modalidade.href)}+location.search+location.hash)</script></body></html>`);
}

mkdirSync(join(dist, 'almoxarifado'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'almoxarifado', 'index.html'));
writeFileSync(join(dist, 'almoxarifado.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/almoxarifado"><title>Almoxarifado</title><a href="/almoxarifado">Abrir almoxarifado</a><script>location.replace("/almoxarifado"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'agenda'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'agenda', 'index.html'));
writeFileSync(join(dist, 'agenda.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/agenda"><title>Agenda</title><a href="/agenda">Abrir agenda</a><script>location.replace("/agenda"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'agendamento-quadras'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'agendamento-quadras', 'index.html'));
writeFileSync(join(dist, 'agendamento-quadras.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/agendamento-quadras"><title>Quadras do Horto</title><a href="/agendamento-quadras">Abrir agendamento</a><script>location.replace("/agendamento-quadras"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'comunicacao'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'comunicacao', 'index.html'));
writeFileSync(join(dist, 'comunicacao.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/comunicacao"><title>Arquivos</title><a href="/comunicacao">Abrir arquivos</a><script>location.replace("/comunicacao"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'detalhes-evento'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'detalhes-evento', 'index.html'));
writeFileSync(join(dist, 'detalhes-evento.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/detalhes-evento"><title>Detalhes do Evento</title><a href="/detalhes-evento">Abrir detalhes</a><script>location.replace("/detalhes-evento"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'downloads'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'downloads', 'index.html'));
writeFileSync(join(dist, 'downloads.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/downloads"><title>Downloads</title><a href="/downloads">Abrir downloads</a><script>location.replace("/downloads"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'frequenciapreenchimento'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'frequenciapreenchimento', 'index.html'));
writeFileSync(join(dist, 'frequenciapreenchimento.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/frequenciapreenchimento"><title>Frequência</title><a href="/frequenciapreenchimento">Abrir frequência</a><script>location.replace("/frequenciapreenchimento"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'importar-imagens'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'importar-imagens', 'index.html'));
writeFileSync(join(dist, 'importar-imagens.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/importar-imagens"><title>Importar imagens</title><a href="/importar-imagens">Abrir importação de imagens</a><script>location.replace("/importar-imagens"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'importar-videos'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'importar-videos', 'index.html'));
writeFileSync(join(dist, 'importar-videos.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/importar-videos"><title>Importar vídeos</title><a href="/importar-videos">Abrir importação de vídeos</a><script>location.replace("/importar-videos"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'presenca'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'presenca', 'index.html'));
writeFileSync(join(dist, 'presenca.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/presenca"><title>Presença</title><a href="/presenca">Abrir presença</a><script>location.replace("/presenca"+location.search+location.hash)</script></html>');
writeFileSync(join(dist, 'esporte.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/"><title>Portal do Esporte</title><a href="/">Abrir Portal do Esporte</a><script>location.replace("/"+location.search+location.hash)</script></html>');
writeFileSync(join(dist, 'esporteteste.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/"><title>Portal do Esporte</title><a href="/">Abrir Portal do Esporte</a><script>location.replace("/"+location.search+location.hash)</script></html>');
for (const legacy of ['index - Copia.html', 'index - Copia1.html', 'teste28.html', 'teste28 (2).html']) {
  writeFileSync(join(dist, legacy), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/"><title>Portal do Esporte</title><a href="/">Abrir Portal do Esporte</a><script>location.replace("/"+location.search+location.hash)</script></html>');
}
mkdirSync(join(dist, 'capi'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'capi', 'index.html'));
writeFileSync(join(dist, 'capi.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/capi"><title>Capi Interativa</title><a href="/capi">Abrir Capi</a><script>location.replace("/capi"+location.search+location.hash)</script></html>');
mkdirSync(join(dist, 'descanso'), { recursive: true });
copyFileSync(join(dist, 'index.html'), join(dist, 'descanso', 'index.html'));
writeFileSync(join(dist, 'descanso.html'), '<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta http-equiv="refresh" content="0;url=/descanso"><title>Descanso</title><a href="/descanso">Abrir descanso</a><script>location.replace("/descanso"+location.search+location.hash)</script></html>');

for (const file of readdirSync(root)) {
  if (extname(file).toLowerCase() === ".html" && !['index.html', 'conquistas.html', 'login.html', 'administrativo.html', 'inscricao.html', 'almoxarifado.html', 'agenda.html', 'agendamento-quadras.html', 'comunicacao.html', 'detalhes-evento.html', 'downloads.html', 'frequenciapreenchimento.html', 'importar-imagens.html', 'importar-videos.html', 'presenca.html', 'esporte.html', 'esporteteste.html', 'capi.html', 'descanso.html', 'index - copia.html', 'index - copia1.html', 'teste28.html', 'teste28 (2).html'].includes(file.toLowerCase()) && !modalidadesEscola.some(item => item.legacyHref === file)) {
    copyFileSync(join(root, file), join(dist, file));
  }
}
