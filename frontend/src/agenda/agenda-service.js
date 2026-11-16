function respostaOuErro(resposta) {
    if (!resposta.ok) throw new Error(resposta.status === 401 || resposta.status === 403 ? 'Entre com uma conta administrativa para alterar a agenda.' : 'Não foi possível salvar o evento.');
    return resposta.status === 204 ? null : resposta.json();
}

export function eventoDaApi(evento) {
    return { id: evento.id, titulo: evento.nome, categoria: evento.categoria, descricao: evento.descricao || '', data: evento.dataEvento, hora: evento.hora, local: evento.local, responsavel: evento.responsavel, materiais: evento.materiais || '' };
}

export function eventoParaApi(evento) {
    return { nome: evento.titulo, categoria: evento.categoria, descricao: evento.descricao, dataEvento: evento.data, hora: evento.hora, local: evento.local, responsavel: evento.responsavel, materiais: evento.materiais };
}

export async function listarEventos(fetcher = fetch) {
    const resposta = await fetcher('/api/eventos', { credentials: 'include', cache: 'no-store' });
    if (!resposta.ok) throw new Error('Não foi possível carregar a agenda compartilhada.');
    return (await resposta.json()).map(eventoDaApi);
}

export async function buscarEvento(id, fetcher = fetch) {
    const resposta = await fetcher(`/api/eventos/${encodeURIComponent(id)}`, { credentials: 'include', cache: 'no-store' });
    if (!resposta.ok) throw new Error('Evento não encontrado.');
    return eventoDaApi(await resposta.json());
}

export async function criarEvento(evento, fetcher = fetch) {
    const resposta = await fetcher('/api/eventos', { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(eventoParaApi(evento)) });
    return eventoDaApi(await respostaOuErro(resposta));
}

export async function atualizarEvento(evento, fetcher = fetch) {
    const resposta = await fetcher(`/api/eventos/${encodeURIComponent(evento.id)}`, { method: 'PUT', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify(eventoParaApi(evento)) });
    return eventoDaApi(await respostaOuErro(resposta));
}

export async function excluirEvento(id, fetcher = fetch) {
    const resposta = await fetcher(`/api/eventos/${encodeURIComponent(id)}`, { method: 'DELETE', credentials: 'include' });
    await respostaOuErro(resposta);
}

export function agruparEventos(eventos) {
    return eventos.reduce((grupos, evento) => { (grupos[evento.data] ||= []).push(evento); return grupos; }, {});
}

export function lerEventosLocais(storage = localStorage) {
    try {
        const dados = JSON.parse(storage.getItem('eventosEsporte'));
        return Object.entries(dados || {}).flatMap(([data, itens]) => (Array.isArray(itens) ? itens : []).map(evento => ({ ...evento, data }))).filter(evento => evento && evento.titulo && evento.data);
    } catch { return []; }
}
