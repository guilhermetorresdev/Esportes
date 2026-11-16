import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const vite = readFileSync(new URL('../vite.config.js', import.meta.url), 'utf8');
const main = readFileSync(new URL('../src/main.jsx', import.meta.url), 'utf8');

test('rotas principais têm middleware e montagem React', () => {
    for (const rota of ['inscricao', 'almoxarifado', 'agenda', 'agendamento-quadras', 'comunicacao', 'esporte-nas-regionais']) {
        assert.match(vite, new RegExp(`/${rota}`));
        assert.match(main, new RegExp(`/${rota}`));
    }
});

test('smoke de rotas inclui entradas legadas', () => {
    const smoke = readFileSync(new URL('../../ops/smoke-frontend.ps1', import.meta.url), 'utf8');
    for (const rota of ['login.html', 'agenda.html', 'inscricao.html', 'almoxarifado.html']) assert.match(smoke, new RegExp(rota.replace('.', '\\.')));
});
