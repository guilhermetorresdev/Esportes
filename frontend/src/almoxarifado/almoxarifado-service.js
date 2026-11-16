function respostaOuErro(resposta, mensagem) {
    if (!resposta.ok) throw new Error(resposta.status === 401 || resposta.status === 403 ? 'Entre com uma conta administrativa para acessar o almoxarifado.' : mensagem);
    return resposta.status === 204 ? null : resposta.json();
}

export async function listarMovimentacoes(fetcher = fetch) {
    const resposta = await fetcher('/api/almoxarifado', { credentials: 'include', cache: 'no-store' });
    return respostaOuErro(resposta, 'Não foi possível carregar as movimentações do almoxarifado.');
}

export async function registrarRetirada(dados, fetcher = fetch) {
    const resposta = await fetcher('/api/almoxarifado/retiradas', { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dados) });
    return respostaOuErro(resposta, 'Não foi possível registrar a retirada.');
}

export async function registrarDevolucao(id, dados, fetcher = fetch) {
    const resposta = await fetcher(`/api/almoxarifado/${encodeURIComponent(id)}/devolucao`, { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(dados) });
    return respostaOuErro(resposta, resposta.status === 409 ? 'A movimentação foi atualizada por outro operador. Recarregue os dados.' : 'Não foi possível registrar a devolução.');
}

export async function enviarFoto(id, arquivo, devolucao = false, fetcher = fetch) {
    const dados = new FormData(); dados.append('arquivo', arquivo);
    const resposta = await fetcher(`/api/almoxarifado/${encodeURIComponent(id)}/foto?devolucao=${devolucao}`, { method: 'POST', credentials: 'include', body: dados });
    return respostaOuErro(resposta, 'Não foi possível enviar a foto.');
}
