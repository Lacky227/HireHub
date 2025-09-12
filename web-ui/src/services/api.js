import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8080/auth',
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
