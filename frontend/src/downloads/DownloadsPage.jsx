import React, { useEffect } from 'react';
import Footer from '../home/Footer.jsx';
import './downloads.css';

const documentos = [
    { icone: 'fa-file-signature', classe: 'downloads-blue', titulo: 'Termo de Ajustamento de Conduta (TAC)', descricao: 'Regulamento para atletas sobre conduta, autorização de imagem e viagens oficiais.', arquivo: '/2026 TERMO DE AJUSTAMENTO DE CONDUTA ATLETAS PREFEITURA MUNICIPAL DE BETIM.pdf', acao: 'Baixar TAC (PDF)' },
    { icone: 'fa-camera', classe: 'downloads-red', titulo: 'Protocolo de Fotos e Vídeos', descricao: 'Orientações para o registro de equipes e envio via WhatsApp para a comunicação.', arquivo: '/PROTOCOLO VÍDEOS E FOTOS PARA VIAGENS SEMES.pdf', acao: 'Baixar Guia (PDF)' },
    { icone: 'fa-heartbeat', classe: 'downloads-green', titulo: 'Questionário PAR-Q', descricao: 'Identificação de necessidade de avaliação clínica antes do início da atividade física.', arquivo: '/PAR Q PVE 2026.pdf', acao: 'Baixar PAR-Q (PDF)' },
    { icone: 'fa-map-marked-alt', classe: 'downloads-yellow', titulo: 'Unidades Esportivas', descricao: 'Lista de endereços e modalidades do Projeto Viva o Esporte.', arquivo: '/Unidades Esportivas Projeto Viva o Esporte  (1).pdf', acao: 'Ver Unidades (PDF)' },
];

export default function DownloadsPage() {
    useEffect(() => { document.title = 'Downloads — Portal do Esporte Betim'; }, []);
    return <><main className="downloads-page" id="conteudo" tabIndex="-1"><header className="downloads-heading"><h1>Documentação<br />Oficial 2026</h1><p>Acesse as diretrizes e formulários da SEMES</p></header><section className="downloads-grid" aria-label="Documentos para download">{documentos.map(documento => <article className="download-card" key={documento.titulo}><div><div className={`download-icon ${documento.classe}`}><i className={`fas ${documento.icone}`} aria-hidden="true" /></div><h2>{documento.titulo}</h2><p>{documento.descricao}</p></div><a href={documento.arquivo} download><i className="fas fa-download" aria-hidden="true" /> {documento.acao}</a></article>)}</section><section className="downloads-notice"><i className="fas fa-info-circle" aria-hidden="true" /><div><h2>Instruções de entrega</h2><p>Certifique-se de que todos os documentos estejam devidamente preenchidos e assinados. Para materiais de mídia, utilize o WhatsApp da comunicação: (31) 9 9327-0326.</p></div></section></main><Footer /></>;
}
