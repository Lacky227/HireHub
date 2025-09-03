import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Bot, Mail, Lock } from 'lucide-react';

export default function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleLogin = (e) => {
        e.preventDefault();
        navigate('/dashboard');
    };

    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center items-center p-4">
            <div className="max-w-md w-full mx-auto">
                <Link to="/" className="flex justify-center items-center mb-8">
                    <Bot className="w-10 h-10 text-indigo-600" />
                    <span className="text-3xl font-bold text-gray-800 ml-2">HireHub</span>
                </Link>
                <div className="bg-white p-8 rounded-2xl shadow-md border border-gray-200">
                    <h2 className="text-3xl font-bold text-center text-gray-800 mb-2">Welcome Back!</h2>
                    <p className="text-center text-gray-500 mb-8">Sign in to continue your journey.</p>
                    <div className="space-y-6">
                        <div className="relative">
                            <Mail className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
                            <input
                                type="email"
                                placeholder="Email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition"
                            />
                        </div>
                        <div className="relative">
                            <Lock className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
                            <input
                                type="password"
                                placeholder="Password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500 focus:border-indigo-500 outline-none transition"
                            />
                        </div>
                        <button
                            onClick={handleLogin}
                            className="w-full bg-indigo-600 text-white py-3 rounded-lg font-semibold hover:bg-indigo-700 transition-transform transform hover:scale-105 shadow-lg"
                        >
                            Login
                        </button>
                    </div>
                    <p className="text-center text-gray-600 mt-6">
                        Don't have an account?{' '}
                        <Link to="/register" className="font-semibold text-indigo-600 hover:underline">
                            Sign Up
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
}