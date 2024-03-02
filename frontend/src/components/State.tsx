import { Alert, Box, CircularProgress } from '@mui/material';
import React from 'react';

type StateProps = {
  isError: boolean;
  isLoading: boolean;
  error: Error | null;
  children: React.ReactNode;
};

const State: React.FC<StateProps> = ({ isError, isLoading, error, children }) => {
  if (isLoading) {
    return (
      <Box sx={{ margin: 5 }}>
        <CircularProgress />
      </Box>
    );
  }

  if (isError) {
    return (
      <Box sx={{ mt: 5, mb: 5 }}>
        <Alert severity="error">Error: {error?.message}</Alert>
      </Box>
    );
  }

  return <>{children}</>;
};

export default State;
