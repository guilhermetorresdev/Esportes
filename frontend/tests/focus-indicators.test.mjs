import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const css = readFileSync(new URL('../public/css/portal-base.css', import.meta.url), 'utf8');

test('cards de Arquivos e eventos da Agenda possuem foco visível', () => {
    assert.match(css, /\.arquivos-card:focus-visible/);
    assert.match(css, /\.agenda-event:focus-visible/);
    assert.match(css, /outline: 3px solid #facc15/);
});
