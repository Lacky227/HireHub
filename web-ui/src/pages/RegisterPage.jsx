import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useSearchParams } from 'react-router-dom';
import { Bot, Mail, Lock, User, Briefcase } from 'lucide-react';
import { registerUser } from '../services/api.js';

export default function RegisterPage() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [role, setRole] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const currentRole = searchParams.get('role');
        if (currentRole) setRole(currentRole);
    }, [searchParams]);

    const handleRoleChange = (newRole) => {
        setRole(newRole);
        setSearchParams({ role: newRole });
    };

    const handleRegister = async (e) => {
        e.preventDefault();

        if (!role || !firstName || !lastName || !email || !password) {
            alert('Please fill all fields');
            return;
        }

        try {
            const response = await registerUser({
                firstName,
                lastName,
                email,
                password,
                role,
            });

            const { accessToken, refreshToken, role: userRole } = response.data;

            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);
            localStorage.setItem('userRole', userRole);

            alert('Registration successful!');
            navigate('/login');
        } catch (err) {
            console.error(err);
            if (err.response?.status === 409) {
                alert('Email already exists');
            } else {
                alert('Registration failed');
            }
        }
    };

    return (
        <div className="min-h-screen bg-gray-50 flex flex-col justify-center items-center p-4">
            <div className="max-w-md w-full mx-auto">
                <Link to="/" className="flex justify-center items-center mb-8">
                    <Bot className="w-10 h-10 text-indigo-600" />
                    <span className="text-3xl font-bold text-gray-800 ml-2">HireHub</span>
                </Link>
                <div className="bg-white p-8 rounded-2xl shadow-md border border-gray-200">
                    <h2 className="text-3xl font-bold text-center text-gray-800 mb-2">Create an Account</h2>
                    <p className="text-center text-gray-500 mb-8">Join us and find your perfect match.</p>
                    <form className="space-y-6" onSubmit={handleRegister}>
                        <div>
                            <label className="block text-sm font-medium text-gray-700 mb-2">I am a:</label>
                            <div className="grid grid-cols-2 gap-4">
                                <button
                                    type="button"
                                    onClick={() => handleRoleChange('Candidate')}
                                    className={`flex items-center justify-center p-3 rounded-lg border-2 transition ${
                                        role === 'Candidate' ? 'border-indigo-600 bg-indigo-50' : 'border-gray-300'
                                    }`}
                                >
                                    <User className={`mr-2 ${role === 'Candidate' ? 'text-indigo-600' : 'text-gray-500'}`} size={20} />
                                    <span className="font-semibold">Candidate</span>
                                </button>
                                <button
                                    type="button"
                                    onClick={() => handleRoleChange('Recruiter')}
                                    className={`flex items-center justify-center p-3 rounded-lg border-2 transition ${
                                        role === 'Recruiter' ? 'border-indigo-600 bg-indigo-50' : 'border-gray-300'
                                    }`}
                                >
                                    <Briefcase className={`mr-2 ${role === 'Recruiter' ? 'text-indigo-600' : 'text-gray-500'}`} size={20} />
                                    <span className="font-semibold">Recruiter</span>
                                </button>
                            </div>
                        </div>

                        <input
                            type="text"
                            placeholder="First Name"
                            value={firstName}
                            onChange={(e) => setFirstName(e.target.value)}
                            className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500"
                            required
                        />
                        <input
                            type="text"
                            placeholder="Last Name"
                            value={lastName}
                            onChange={(e) => setLastName(e.target.value)}
                            className="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500"
                            required
                        />
                        <div className="relative">
                            <Mail className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" size={20} />
                            <input
                                type="email"
                                placeholder="Email"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                                className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500"
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
                                className="w-full pl-10 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-indigo-500"
                            />
                        </div>
                        <button
                            type="submit"
                            className="w-full bg-indigo-600 text-white py-3 rounded-lg font-semibold hover:bg-indigo-700 transition-transform transform hover:scale-105 shadow-lg"
                        >
                            Create Account
                        </button>
                    </form>
                    <p className="text-center text-gray-600 mt-6">
                        Already have an account?{' '}
                        <Link to="/login" className="font-semibold text-indigo-600 hover:underline">
                            Login
                        </Link>
                    </p>
                </div>
            </div>
        </div>
    );
}