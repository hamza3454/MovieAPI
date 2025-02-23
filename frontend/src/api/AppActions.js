import { observable, action } from "mobx";

const AppStore = observable({
    authToken: null,
});

const AppActions = {
    setAuthToken: action((token) => {
        AppStore.authToken = token;
        localStorage.setItem("token", token);
    }),
    getLocalStorageAuthToken: action(() => {
        const token = localStorage.getItem("token");

        if (!token) {
            return false;
        }
        AppActions.setAuthToken(token);
        return true;
    }),
};

export {AppActions, AppStore};
