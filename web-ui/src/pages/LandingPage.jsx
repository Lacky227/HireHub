import React from 'react';
import { Search, User, Briefcase, MessageSquare, ArrowRight, Bot, Clock, Zap } from 'lucide-react';
import { Link } from 'react-router-dom';

const Header = () => (
    <header className="bg-white/80 backdrop-blur-md fixed top-0 left-0 right-0 z-50 border-b border-gray-200">
        <div className="container mx-auto px-6 py-4 flex justify-between items-center">
            <div className="flex items-center space-x-2">
                <Bot className="w-8 h-8 text-indigo-600" />
                <span className="text-2xl font-bold text-gray-800">HireHub</span>
            </div>
            <nav className="hidden md:flex items-center space-x-4">
                <Link to="/login" className="text-gray-600 hover:text-indigo-600 transition-colors">
                    Sign In
                </Link>
                <Link to="/register" className="bg-indigo-600 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 transition-colors shadow-sm">
                    Sign Up
                </Link>
            </nav>
            <button className="md:hidden text-gray-800">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16m-7 6h7" />
                </svg>
            </button>
        </div>
    </header>
);

const Hero = () => (
    <section className="pt-32 pb-20 text-center bg-white">
        <div className="container mx-auto px-6">
            <h1 className="text-4xl md:text-6xl font-extrabold text-gray-900 leading-tight mb-4">
                AI-driven hiring <span className="text-indigo-600">without noise</span>
            </h1>
            <p className="text-lg md:text-xl text-gray-600 max-w-3xl mx-auto mb-8">
                We connect top talent with leading companies using AI, eliminating noise in the hiring process.
            </p>
            <div className="flex flex-col sm:flex-row justify-center items-center space-y-4 sm:space-y-0 sm:space-x-4">
                <Link to="/register?role=Candidate" className="w-full sm:w-auto bg-indigo-600 text-white px-8 py-3 rounded-lg text-lg font-semibold hover:bg-indigo-700 transition-transform transform hover:scale-105 shadow-lg flex items-center justify-center">
                    <Briefcase className="w-5 h-5 mr-2" /> I'm looking for a job
                </Link>
                <Link to="/register?role=Recruiter" className="w-full sm:w-auto bg-gray-800 text-white px-8 py-3 rounded-lg text-lg font-semibold hover:bg-gray-900 transition-transform transform hover:scale-105 shadow-lg flex items-center justify-center">
                    <User className="w-5 h-5 mr-2" /> I'm looking for talent
                </Link>
            </div>
        </div>
    </section>
);

const FeatureCard = ({ icon, title, description }) => (
    <div className="bg-white p-8 rounded-xl shadow-md border border-gray-100 hover:shadow-lg hover:-translate-y-1 transition-all duration-300">
        <div className="bg-indigo-100 text-indigo-600 w-14 h-14 rounded-full flex items-center justify-center mb-5">
            {icon}
        </div>
        <h3 className="text-2xl font-bold text-gray-800 mb-3">{title}</h3>
        <p className="text-gray-600 leading-relaxed">{description}</p>
    </div>
);

const HowItWorks = () => (
    <section className="py-20 bg-gray-50">
        <div className="container mx-auto px-6">
            <div className="text-center mb-12">
                <h2 className="text-3xl md:text-4xl font-bold text-gray-900">How It Works</h2>
                <p className="text-lg text-gray-600 mt-2">Three simple steps to your perfect match.</p>
            </div>
            <div className="grid md:grid-cols-3 gap-8">
                <FeatureCard
                    icon={<Zap size={28} />}
                    title="AI Matches Jobs/Candidates"
                    description="Our intelligent algorithm analyzes your skills and requirements to find the best matches."
                />
                <FeatureCard
                    icon={<Clock size={28} />}
                    title="Expiring Jobs & Resumes"
                    description="We ensure offers stay current. All listings are active for a limited time, encouraging quick action."
                />
                <FeatureCard
                    icon={<MessageSquare size={28} />}
                    title="Direct Contact"
                    description="No middlemen. Candidates and recruiters communicate directly for maximum efficiency."
                />
            </div>
        </div>
    </section>
);

const Footer = () => (
    <footer className="bg-gray-900 text-white">
        <div className="container mx-auto px-6 py-12">
            <div className="grid md:grid-cols-3 gap-8">
                <div>
                    <div className="flex items-center space-x-2 mb-4">
                        <Bot className="w-7 h-7" />
                        <span className="text-xl font-bold">TalentHub AI</span>
                    </div>
                    <p className="text-gray-400">AI-driven hiring without noise.</p>
                </div>
                <div>
                    <h4 className="font-semibold text-lg mb-4">Links</h4>
                    <ul>
                        <li className="mb-2"><Link to="#" className="text-gray-400 hover:text-white">Terms of Service</Link></li>
                        <li className="mb-2"><Link to="#" className="text-gray-400 hover:text-white">Privacy Policy</Link></li>
                        <li className="mb-2"><Link to="#" className="text-gray-400 hover:text-white">Contact Us</Link></li>
                    </ul>
                </div>
                <div>
                    <h4 className="font-semibold text-lg mb-4">Follow Us</h4>
                    <div className="flex space-x-4">
                        <Link to="#" className="text-gray-400 hover:text-white">Twitter</Link>
                        <Link to="#" className="text-gray-400 hover:text-white">LinkedIn</Link>
                        <Link to="#" className="text-gray-400 hover:text-white">Facebook</Link>
                    </div>
                </div>
            </div>
            <div className="border-t border-gray-800 mt-8 pt-6 text-center text-gray-500">
                <p>&copy; {new Date().getFullYear()} HireHub. All rights reserved.</p>
            </div>
        </div>
    </footer>
);

export default function LandingPage() {
    return (
        <div className="bg-white font-sans">
            <Header />
            <main>
                <Hero />
                <HowItWorks />
            </main>
            <Footer />
        </div>
    );
}