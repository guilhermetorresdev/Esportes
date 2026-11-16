import { test } from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const catalogo = JSON.parse(readFileSync(new URL('../public/data/regionais.json', import.meta.url)));
const pins = JSON.parse(readFileSync(new URL('../src/regionais/equipamentos-pins.json', import.meta.url))).pins;

test('coordenadas verificadas têm fonte, IDs únicos e não inventam posições pendentes', () => {
    assert.equal(new Set(pins.map(pin => pin.id)).size, pins.length);
    assert.equal(new Set(pins.map(pin => pin.coordenadas.join(','))).size, pins.length);
    for (const pin of pins) {
        assert.ok(catalogo.equipamentos.some(item => item.id === pin.id));
        assert.match(pin.fonte, /^https:\/\/www\.openstreetmap\.org\/(?:node|way|relation)\/\d+$/);
        assert.equal(pin.coordenadas.length, 2);
        assert.ok(pin.coordenadas.every(Number.isFinite));
    }
    const idsComPin = new Set(pins.map(pin => pin.id));
    const semPinVerificado = catalogo.equipamentos.filter(item => !idsComPin.has(item.id));
    assert.equal(semPinVerificado.length, 21);
    assert.ok(semPinVerificado.every(item => !item.coordenadas));
});
