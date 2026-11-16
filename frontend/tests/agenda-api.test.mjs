import test from 'node:test';
import assert from 'node:assert/strict';
import { agruparEventos, eventoDaApi, eventoParaApi } from '../src/agenda/agenda-service.js';

test('converte evento da API para o formato visual e agrupa por data', () => {
    const visual = eventoDaApi({ id: 7, nome: 'Festival', categoria: 'Sub-15', dataEvento: '2026-12-10', hora: '14:00', local: 'Horto', responsavel: 'Semes' });
    assert.equal(visual.titulo, 'Festival');
    assert.deepEqual(Object.keys(agruparEventos([visual])), ['2026-12-10']);
});

test('converte evento visual para o contrato da API', () => {
    const api = eventoParaApi({ titulo: 'Festival', categoria: 'Sub-15', data: '2026-12-10', hora: '14:00', local: 'Horto', responsavel: 'Semes', descricao: '', materiais: '' });
    assert.equal(api.nome, 'Festival');
    assert.equal(api.dataEvento, '2026-12-10');
});
