import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const source = readFileSync(new URL('../src/agenda/AgendaSharedPage.jsx', import.meta.url), 'utf8');

test('modal da agenda fecha com Escape e restaura o scroll', () => {
    assert.match(source, /event\.key === 'Escape'/);
    assert.match(source, /document\.body\.style\.overflow/);
    assert.match(source, /document\.removeEventListener\('keydown'/);
});

test('eventos da agenda são operáveis pelo teclado', () => {
    assert.match(source, /className="agenda-event" role="button"/);
    assert.match(source, /tabIndex=\{passado \? -1 : 0\}/);
    assert.match(source, /e\.key === 'Enter' \|\| e\.key === ' '/);
});
