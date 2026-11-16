import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const css = readFileSync(new URL('../public/css/portal-base.css', import.meta.url), 'utf8');

test('portal respeita movimento reduzido', () => {
    assert.match(css, /prefers-reduced-motion: reduce/);
    assert.match(css, /animation-duration: \.01ms/);
    assert.match(css, /scroll-behavior: auto/);
    assert.match(css, /transition-duration: \.01ms/);
});

test('modais respeitam a largura e altura da viewport', () => {
    assert.match(css, /\.agenda-modal, \.almox-modal, \.inscricao-modal \{ max-width: calc\(100vw - 2rem\)/);
    assert.match(css, /max-height: calc\(100vh - 2rem\)/);
});
