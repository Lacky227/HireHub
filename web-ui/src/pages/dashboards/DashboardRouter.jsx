import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function DashboardRouter() {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const accessToken = localStorage.getItem('accessToken');
        const userRole = localStorage.getItem('userRole');
        const userEmail = localStorage.getItem('userEmail');

        if (accessToken && userRole && userEmail) {
            setUser({ email: userEmail, role: userRole.toUpperCase() });
        } else {
            navigate('/login');
        }
    }, [navigate]);

    if (!user) {
        return <div className="flex justify-center items-center min-h-screen">Loading...</div>;
    }

    if (user.role === 'CANDIDATE') {
    }

    if (user.role === 'RECRUITER') {
    }

    navigate('/login');
    return null;
}