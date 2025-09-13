import React, { useEffect, useState } from "react";
import {useNavigate} from "react-router-dom";

export default function DashboardRouter(){
    const [userRole, setUserRole] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const storedRole = localStorage.getItem('userRole');
        if (storedRole){
            setUserRole(JSON.parse(storedRole));
        } else {
            navigate('/login');
        }
    }, [navigate]);
    if (!userRole){
        return <div>Loading...</div>
    }

    if (userRole.role === 'CANDIDATE'){
    }
    if (userRole.role === 'RECRUITER'){
    }
    return navigate('/login');
}