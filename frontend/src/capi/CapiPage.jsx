import React, { useEffect, useRef, useState } from 'react';
import './capi.css';

const frases = ['🏃‍♀️ Bora praticar esporte!', '💪 Movimento é saúde!', '🎯 Já viu os projetos da Secretaria?', '⚽ Vem participar das atividades!', '🏆 Betim é esporte!'];

export default function CapiPage() {
    const capi = useRef(null); const timer = useRef(null); const [fala, setFala] = useState(''); const [visivel, setVisivel] = useState(false); const [espelhado, setEspelhado] = useState(false);
    useEffect(() => { const mostrar = duracao => { setFala(frases[Math.floor(Math.random() * frases.length)]); setVisivel(true); clearTimeout(timer.current); timer.current = setTimeout(() => setVisivel(false), duracao); }; const mouse = event => { const rect = capi.current?.getBoundingClientRect(); if (rect) setEspelhado(event.clientX < rect.left); }; const intervalo = setInterval(() => mostrar(2500), 8000); document.addEventListener('mousemove', mouse); return () => { clearInterval(intervalo); clearTimeout(timer.current); document.removeEventListener('mousemove', mouse); }; }, []);
    const clicar = () => { setFala(frases[Math.floor(Math.random() * frases.length)]); setVisivel(true); clearTimeout(timer.current); timer.current = setTimeout(() => setVisivel(false), 3000); };
    return <main className="capi-page" id="conteudo" tabIndex="-1"><div className={`capi-speech ${visivel ? 'visible' : ''}`} role="status" aria-live="polite">{fala}</div><button type="button" className={`capi-character ${espelhado ? 'flipped' : ''}`} ref={capi} onClick={clicar} aria-label="Conversar com a Capi"><img src="/capi.png" alt="Capi, mascote do esporte de Betim" /></button></main>;
}
