import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const almox = readFileSync(new URL('../src/almoxarifado/AlmoxarifadoPage.jsx', import.meta.url), 'utf8');
const inscricao = readFileSync(new URL('../src/inscricao/InscricaoPage.jsx', import.meta.url), 'utf8');

test('Almoxarifado gerencia foco, Escape e scroll no modal', () => {
    assert.match(almox, /almox-modal input, \.almox-modal textarea, \.almox-modal button/);
    assert.match(almox, /event\.key === 'Escape'/);
    assert.match(almox, /document\.body\.style\.overflow/);
});

test('ficha de inscrição gerencia foco, Escape e scroll no modal', () => {
    assert.match(inscricao, /inscricao-modal input, \.inscricao-modal select/);
    assert.match(inscricao, /event\.key === 'Escape'/);
    assert.match(inscricao, /document\.body\.style\.overflow/);
});
