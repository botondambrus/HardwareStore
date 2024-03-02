import React, { createContext, useState, useEffect, ReactNode } from 'react';
import Alert from '@mui/material/Alert';

export const AlertContext = createContext<(message: string, severity: 'success' | 'error') => void>(() => {});

type AlertProviderProps = {
  children: ReactNode;
};

type AlertState = {
  message: string | null;
  severity: 'success' | 'error';
};

export const AlertProvider: React.FC<AlertProviderProps> = ({ children }) => {
  const [alert, setAlert] = useState<AlertState>({
    message: null,
    severity: 'success',
  });

  useEffect(() => {
    if (alert.message) {
      const timer = setTimeout(() => {
        setAlert((prev) => ({ ...prev, message: null }));
      }, 3000);

      return () => clearTimeout(timer);
    }
  }, [alert.message]);

  const setMessage = (message: string, severity: 'success' | 'error') => {
    setAlert({ message, severity });
  };

  return (
    <AlertContext.Provider value={setMessage}>
      {alert.message && (
        <Alert severity={alert.severity} sx={{ mt: 3 }}>
          {alert.message}
        </Alert>
      )}
      {children}
    </AlertContext.Provider>
  );
};
