import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';

const nginx = readFileSync(new URL('../nginx.conf', import.meta.url), 'utf8');
const smoke = readFileSync(new URL('../../ops/smoke-prod.ps1', import.meta.url), 'utf8');

test('Nginx preserva proxy, autenticação e compatibilidade das rotas legadas', () => {
    assert.match(nginx, /location \/api\//);
    assert.match(nginx, /proxy_pass http:\/\/backend:8080\/api\//);
    assert.match(nginx, /location = \/login/);
    assert.match(nginx, /location = \/logout/);
    assert.match(nginx, /location = \/futsal\.html \{ return 301 \/futsal\$is_args\$args; \}/);
    assert.match(nginx, /try_files \$uri \$uri\/index\.html \$uri\/ =404/);
});

test('smoke test verifica rotas públicas e sessão sem dados fixos', () => {
    assert.match(smoke, /api\/auth\/csrf/);
    assert.match(smoke, /XSRF-TOKEN/);
    assert.match(smoke, /\/login/);
    assert.match(smoke, /\/logout/);
    assert.match(smoke, /param\(/);
});
