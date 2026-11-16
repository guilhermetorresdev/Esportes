import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const source = readFileSync(new URL('../src/inscricao/InscricaoPage.jsx', import.meta.url), 'utf8');

test('inscrição apresenta cards e abre a ficha do aluno em diálogo', () => {
    assert.match(source, /title="Aluno" onClick=\{\(\) => setAlunoAberto\(true\)\}/);
    assert.match(source, /title="Instrutor"/);
    assert.match(source, /title="Núcleos"/);
    assert.match(source, /role="dialog" aria-modal="true"/);
    assert.match(source, /aria-labelledby="ficha-aluno-titulo"/);
    assert.match(source, /aria-label="Fechar ficha de inscrição"/);
});
