import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const ler = caminho => readFileSync(new URL(caminho, import.meta.url), 'utf8');
const login = ler('../src/login/LoginPage.jsx');
const arquivos = ler('../src/comunicacao/ArquivosPage.jsx');
const horto = ler('../src/horto/HortoPage.jsx');
const regionais = ler('../src/regionais/EquipmentCard.jsx');

test('fluxos principais anunciam erro, sucesso ou carregamento', () => {
    assert.match(login, /role="alert"/);
    assert.match(arquivos, /role="status" aria-live="polite"/);
    assert.match(horto, /role="status" aria-live="polite"/);
});

test('detalhes das Regionais gerenciam foco de abertura e retorno', () => {
    assert.match(regionais, /node\.showModal\(\)/);
    assert.match(regionais, /node\.querySelector\('button, a'\)\?\.focus\(\)/);
    assert.match(regionais, /origem\?\.focus\(\{ preventScroll: true \}\)/);
});
