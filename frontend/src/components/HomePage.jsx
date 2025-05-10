import { useState } from 'react';
import i18n from 'i18next';
import logo from '../assets/cambyze_icon.png';
import favicon from '../assets/favicon.ico';
import { useTranslation } from 'react-i18next';

// Dynamically set favicon in head
const link = document.querySelector("link[rel~='icon']");
if (link) {
  link.href = favicon;
}

export default function HomePage() {
  const { t } = useTranslation();
  const [status, setStatus] = useState('idle');

  // Function called when submiting the contact button
  const handleSubmit = async e => {
    e.preventDefault();
    setStatus('sending');
    const form = e.target;
    const formData = new URLSearchParams(new FormData(form));

    try {
      const res = await fetch('/contact', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: formData.toString(),
      });
      if (res.ok) {
        setStatus('sent');
        form.reset();
      } else {
        throw new Error((await res.text()));
      }
    } catch (err) {
      console.error(err);
      setStatus('error');
    }
  };

  return (
    <div className="min-h-screen flex flex-col bg-white text-gray-800 font-sans">
      {/* Header */}
      <header className="bg-[#8EB4E3] text-white shadow">
        <div className="container mx-auto px-6 py-4 flex justify-between items-center">
          <a href="/" className="flex items-center space-x-3">
            <img
              src={logo}
              alt="Cambyze Logo"
              className="h-16 md:h-20 object-contain"
            />
          </a>
          <nav>
            <ul className="flex space-x-8 text-sm font-medium">
              <li>
                <a href="#services" className="hover:underline">{t("about_services")}</a>
              </li>
              <li><a href="#about" className="hover:underline">{t("about_menu")}</a></li>
              <li>
                <a href="#contact" className="hover:underline">{t("contact_menu")}</a>
              </li>
            </ul>
          </nav>

          {/* Language Switcher */}
          <div className="text-sm">
            <button
              onClick={() => i18n.changeLanguage('en')}
              className="mr-2 hover:underline"
            >
              EN
            </button>
            <button
              onClick={() => i18n.changeLanguage('fr')}
              className="hover:underline"
            >
              FR
            </button>
          </div>

        </div>
      </header>

      {/* Hero Section */}
      <section className="flex-1 flex items-center justify-center bg-gray-50 px-6 py-20">
        <div className="text-center max-w-2xl mx-auto">
          <h2 className="text-5xl font-bold mb-6 leading-tight">
            {t('hero_title')}
          </h2>
          <p className="text-lg text-gray-600 mb-8">{t('hero_desc')}</p>
          <a href="#contact" className="inline-block px-8 py-3 bg-[#8EB4E3] text-white text-lg font-medium rounded-lg shadow hover:bg-blue-500 transition-colors">
            {t('get_started')}
          </a>
        </div>
      </section>

      {/* Services Section */}
      <section id="services" className="bg-white py-16">
        <div className="container mx-auto px-6">
          <h3 className="text-3xl font-semibold text-center mb-12">
            {t("services_title")}
          </h3>
          <div className="grid gap-8 grid-cols-1 md:grid-cols-3">
            <div className="p-6 border rounded-lg shadow hover:shadow-lg transition-shadow">
              <h4 className="text-xl font-semibold mb-3">{t("service_web")}</h4>
              <p className="text-gray-600">{t("service_web_desc")}</p>
            </div>
            <div className="p-6 border rounded-lg shadow hover:shadow-lg transition-shadow">
              <h4 className="text-xl font-semibold mb-3">{t("service_mobile")}</h4>
              <p className="text-gray-600">{t("service_mobile_desc")}</p>
            </div>
            <div className="p-6 border rounded-lg shadow hover:shadow-lg transition-shadow">
              <h4 className="text-xl font-semibold mb-3">{t("service_seo")}</h4>
              <p className="text-gray-600">{t("service_seo_desc")}</p>
            </div>
          </div>
        </div>
      </section>

      {/* About Section */}
      <section id="about" className="bg-gray-100 py-16">
        <div className="container mx-auto px-6 text-center">
          <h3 className="text-3xl font-semibold mb-6">{t("about_title")}</h3>
          <p className="max-w-3xl mx-auto text-gray-700 leading-relaxed">{t("about_desc")}</p>
        </div>
      </section>

      {/* Contact Section */}
      <section id="contact" className="bg-white py-16">
        <div className="container mx-auto px-6">
          <h3 className="text-3xl font-semibold text-center mb-6">{t("contact_title")}</h3>
          {/* On submit calls the api which sends mail*/}
          <form onSubmit={handleSubmit} className="max-w-xl mx-auto grid gap-6">
            <input
              name="name"
              type="text"
              placeholder={t("contact_name")}
              className="w-full p-4 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <input
              name="email"
              type="email"
              placeholder={t("contact_mail")}
              className="w-full p-4 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <textarea
              name="message"
              placeholder={t("contact_message")}
              rows="5"
              className="w-full p-4 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <button
              type="submit"
              className="w-full px-6 py-3 bg-[#8EB4E3] text-white text-lg font-medium rounded-lg shadow hover:bg-[#76a0c9] transition-colors"
              disabled={status === 'sending'}
            >
              {status === 'sending'
                ? t("sending")
                : status === 'sent' ? t("sent") : t("contact_button")}
            </button>
            {status === 'error' &&
              <p className="text-red-600 text-center">{t("error")}</p>}
          </form>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-[#8EB4E3] text-white">
        <div className="container mx-auto px-6 py-6 text-center">
          &copy; {new Date().getFullYear()} Cambyze. All rights reserved.
        </div>
      </footer>
    </div>
  );
}
