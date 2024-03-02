import LaptopIcon from '@mui/icons-material/Laptop';
import {
  AppBar,
  Box,
  Button,
  FormControl,
  MenuItem,
  Select,
  SelectChangeEvent,
  Toolbar,
  Typography,
} from '@mui/material';
import { useTranslation } from 'react-i18next';
import { Link } from 'react-router-dom';
import RO from 'country-flag-icons/react/3x2/RO';
import HU from 'country-flag-icons/react/3x2/HU';
import US from 'country-flag-icons/react/3x2/US';
import { ThemeName } from '../hooks/useThemeSwitcher';

type Props = {
  children: React.ReactNode;
  changeTheme: (event: SelectChangeEvent<string>) => void;
  themeName: ThemeName;
};

const Navbar: React.FC<Props> = ({ children, changeTheme, themeName }) => {
  const { t, i18n } = useTranslation();

  const changeLanguage = (event: SelectChangeEvent<string>) => {
    i18n.changeLanguage(event.target.value);
  };

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <LaptopIcon style={{ marginRight: 5 }} />
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            {t('navbar.title')}
          </Typography>
          <Button color="inherit" sx={{ marginLeft: 1 }}>
            <Link to="/" style={{ color: 'white', textDecoration: 'none' }}>
              {t('navbar.home')}
            </Link>
          </Button >
          <Button color="inherit">
            <Link to="/create" style={{ color: 'white', textDecoration: 'none' }}>
              {t('navbar.create_item')}
            </Link>
          </Button>
          <FormControl sx={{ marginLeft: 1 }}>
            <Select
              value={i18n.language}
              onChange={changeLanguage}
              renderValue={(value) => {
                switch (value) {
                  case 'en':
                    return (
                      <>
                        <US style={{ width: '30px', height: '20px' }} />
                      </>
                    );
                  case 'ro':
                    return (
                      <>
                        <RO style={{ width: '30px', height: '20px' }} />
                      </>
                    );
                  case 'hu':
                    return (
                      <>
                        <HU style={{ width: '30px', height: '20px' }} />
                      </>
                    );
                  default:
                    return null;
                }
              }}
            >
              <MenuItem value={'en'}>
                <US />
              </MenuItem>
              <MenuItem value={'ro'}>
                <RO />
              </MenuItem>
              <MenuItem value={'hu'}>
                <HU />
              </MenuItem>
            </Select>
          </FormControl>
          <FormControl sx={{ marginLeft: 1 }}>
            <Select value={themeName} onChange={changeTheme} sx={{ color: 'white' }}>
              <MenuItem value={'light'}>
                <Typography sx={{ flexGrow: 1 }}>
                  {t('navbar.light_mode')}
                </Typography>
              </MenuItem>
              <MenuItem value={'dark'}>
                <Typography  sx={{ flexGrow: 1 }}>
                  {t('navbar.dark_mode')}
                </Typography>
              </MenuItem>
              <MenuItem value={'custom'}>
                <Typography sx={{ flexGrow: 1 }}>
                  {t('navbar.orange_mode')}
                </Typography>
              </MenuItem>
            </Select>
          </FormControl>
        </Toolbar>
      </AppBar>
      <Box>{children}</Box>
    </>
  );
};

export default Navbar;
