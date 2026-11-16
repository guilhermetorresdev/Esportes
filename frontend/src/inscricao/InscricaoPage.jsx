import React, { useEffect, useMemo, useState } from 'react';
import data from './form-data.json';
import Footer from '../home/Footer.jsx';
import { dataLocal } from './inscricao-service.js';
import './inscricao.css';

export function valoresIniciais(search = '') {
    const valores = Object.fromEntries(data.sections.flatMap(section => section.campos).map(campo => [campo.name, campo.options?.[0]?.value || '']));
    const modalidade = new URLSearchParams(search).get('modalidade');
    valores.modalidade = Object.hasOwn(data.config, modalidade) ? modalidade : '';
    valores.nucleo = ''; valores.dataDeclaracao = dataLocal(); return valores;
}

function Campo({ campo, valor, onChange }) {
    if (campo.type === 'checkbox') return <label className="inscricao-modal-check"><input type="checkbox" checked={valor === 'sim'} onChange={event => onChange(campo.name, event.target.checked ? 'sim' : '')} required={campo.required} /><span>{campo.label}</span></label>;
    const props = { id: campo.name, name: campo.name, value: valor || '', onChange: event => onChange(campo.name, event.target.value), required: campo.required, type: campo.type, placeholder: campo.placeholder, inputMode: campo.inputMode, maxLength: campo.maxLength };
    return <label className="inscricao-modal-field"><span>{campo.label}{campo.required && <b aria-hidden="true"> *</b>}</span>{campo.type === 'textarea' ? <textarea {...props} rows="3" /> : campo.type === 'select' ? <select {...props}><option value="">Selecione</option>{campo.options?.filter(option => option.value).map(option => <option key={option.value} value={option.value}>{option.text}</option>)}</select> : <input {...props} />}</label>;
}

function FichaAluno({ onClose }) {
    const [valores, setValores] = useState(() => valoresIniciais(window.location.search));
    const [enviado, setEnviado] = useState(false);
    useEffect(() => { const fechar = event => event.key === 'Escape' && onClose(); document.addEventListener('keydown', fechar); const anterior = document.body.style.overflow; document.body.style.overflow = 'hidden'; document.querySelector('.inscricao-modal input, .inscricao-modal select, .inscricao-modal textarea, .inscricao-modal button')?.focus(); return () => { document.removeEventListener('keydown', fechar); document.body.style.overflow = anterior; }; }, [onClose]);
    const alterar = (name, value) => setValores(prev => ({ ...prev, [name]: value }));
    return <div className="inscricao-modal-backdrop" role="presentation" onMouseDown={event => event.target === event.currentTarget && onClose()}><section className="inscricao-modal" role="dialog" aria-modal="true" aria-labelledby="ficha-aluno-titulo"><header className="inscricao-modal-head"><div><span>Ficha de inscrição</span><h2 id="ficha-aluno-titulo">Aluno</h2></div><button type="button" className="inscricao-modal-close" onClick={onClose} aria-label="Fechar ficha de inscrição">×</button></header><form onSubmit={event => { event.preventDefault(); setEnviado(true); }}>
        {data.sections.map(section => <fieldset className="inscricao-modal-section" key={section.titulo}><legend>{section.titulo}</legend><div className="inscricao-modal-grid">{section.campos.map(campo => <Campo key={campo.name} campo={campo} valor={valores[campo.name]} onChange={alterar} />)}</div></fieldset>)}
        {enviado && <p className="inscricao-modal-success" role="status">Ficha preenchida. O envio será conectado ao fluxo da Secretaria na próxima etapa.</p>}<div className="inscricao-modal-actions"><button type="button" className="inscricao-secondary" onClick={onClose}>Fechar</button><button type="submit">Salvar ficha</button></div>
    </form></section></div>;
}

function Card({ icon, title, onClick }) { const Tag = onClick ? 'button' : 'article'; const instrutorIcon = title === 'Instrutor' && <svg className="inscricao-card-icon" viewBox="0 0 48 48" aria-hidden="true"><circle cx="24" cy="12" r="5" /><path d="M14 39c1-8 5-13 10-13s9 5 10 13M8 8h10v16H8zM11 13h4m-4 5h4" /></svg>; return <Tag type={onClick ? 'button' : undefined} className="inscricao-menu-card" onClick={onClick}>{instrutorIcon || <i className={`fas ${icon}`} aria-hidden="true" />}<h2>{title}</h2></Tag>; }

export default function InscricaoPage({ initialSearch = typeof window === 'undefined' ? '' : window.location.search } = {}) {
    const contexto = data.config[new URLSearchParams(initialSearch).get('modalidade')]; const [alunoAberto, setAlunoAberto] = useState(false);
    useEffect(() => { document.title = 'Inscrição — Programa Viva Esportes — Betim'; }, []);
    return <><main id="conteudo" className="inscricao-page inscricao-page-empty" tabIndex={-1}><a className="inscricao-back" href={contexto?.voltarHref || '/'}>← {contexto?.voltarTexto || 'Voltar para o Portal'}</a><header className="inscricao-heading"><h1>Ficha de inscrição</h1><p>Selecione uma opção para continuar.</p></header><section className="inscricao-menu" aria-label="Opções de inscrição"><Card icon="fa-user" title="Aluno" onClick={() => setAlunoAberto(true)} /><Card icon="fa-person-chalkboard" title="Instrutor" /><Card icon="fa-building" title="Núcleos" /></section></main><Footer />{alunoAberto && <FichaAluno onClose={() => setAlunoAberto(false)} />}</>;
}
