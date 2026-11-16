import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const script = readFileSync(new URL('../public/js/portal-header.js', import.meta.url), 'utf8');

test('cliente global solicita token CSRF e envia header em mutações', () => {
    assert.match(script, /\/api\/auth\/csrf/);
    assert.match(script, /X-XSRF-TOKEN/);
    assert.match(script, /XMLHttpRequest\.prototype\.send/);
    assert.match(script, /credentials: init\.credentials \|\| 'include'/);
});
