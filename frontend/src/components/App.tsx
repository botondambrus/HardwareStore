import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { queryClient } from '../query/common.query';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import HardwareStore from './HardwareStore/HardwareStore';
import { Container } from '@mui/material';
import ItemDetails from './HardwareStore/ItemDetails';
import CreateItem from './HardwareStore/CreateItem';
import EditItem from './HardwareStore/EditItem';
import PurchaseList from './Purchase/PurchaseList';
import Navbar from './Navbar';
import PurchaseCreate from './Purchase/PurchaseCreate';
import PurchaseEdit from './Purchase/PurchaseEdit';
import { ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { AlertProvider } from '../context/AlertContext';
import { useThemeSwitcher } from '../hooks/useThemeSwitcher';

const App: React.FC = () => {
  const { theme, themeName, changeTheme } = useThemeSwitcher();
  
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <QueryClientProvider client={queryClient}>
        <Container maxWidth="xl">
          <BrowserRouter>
            <Navbar changeTheme={changeTheme} themeName={themeName}>
              <AlertProvider>
                <Routes>
                  <Route path="/" element={<HardwareStore/>} />
                  <Route path="/details/:id" element={<ItemDetails/>} />
                  <Route path="/create" element={<CreateItem />} />
                  <Route path="/edit/:id" element={<EditItem />} />
                  <Route path="/purchase/:id" element={<PurchaseList />} />
                  <Route path="/purchase/:id/edit/:purchaseId" element={<PurchaseEdit />} />
                  <Route path="/purchase/:id/create" element={<PurchaseCreate />} />
                  <Route path="*" element={<h1>Not Found</h1>} />
                </Routes>
              </AlertProvider>
            </Navbar>
          </BrowserRouter>

          <ReactQueryDevtools initialIsOpen={false} />
        </Container>
      </QueryClientProvider>
    </ThemeProvider>
  );
};

export default App;
