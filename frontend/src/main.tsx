import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './components/App.tsx'
import "./i18n"

if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('/sw.js', { scope: '/' });
}

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
