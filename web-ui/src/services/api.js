import axios from 'axios';

const api = axios.create({
    baseURL: 'https://483b0114e2ca.ngrok-free.app/auth',
});

export const registerUser = (userData) => {
    const dataToSend = {
        ...userData,
        role: userData.role.toUpperCase(),
    };
    return api.post('/register', dataToSend);
};

export const loginUser = (credentials) => {
    return api.post('/login', credentials);
};

export const refreshToken = (token) => {
    return api.post('/refresh', { refreshToken: token });
};

export default api;
