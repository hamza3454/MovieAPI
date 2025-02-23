import axios from "axios";
import {AppActions, AppStore} from "./AppActions";

const axiosInstance = axios.create({
    baseURL: "http://localhost:8080", // Change to the actual API URL when deploying
    //baseURL:'http://52.14.50.30:8080',
});

// **Request Interceptor**: Attach Authorization token before requests
axiosInstance.interceptors.request.use(
    async (config) => {
        // Ensure token is fetched from localStorage
        AppActions.getLocalStorageAuthToken();

        const authToken = AppStore.authToken;
        if (authToken) {
            config.headers.Authorization = `Bearer ${authToken}`;
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// **Response Interceptor** (Optional: Handle 401 Unauthorized errors)
axiosInstance.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            console.error("Unauthorized! Redirecting to login...");
            // Handle logout or redirection logic here (e.g., clear token, redirect to login page)
        }
        return Promise.reject(error);
    }
);

export default axiosInstance;


