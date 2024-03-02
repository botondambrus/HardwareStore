import { useContext } from "react";
import { AlertContext } from "../context/AlertContext";

export const useAlert = () => {
    const setMessage = useContext(AlertContext);
    if (setMessage === undefined) {
      throw new Error('useAlert must be used within a AlertProvider');
    }
    return setMessage;
  };