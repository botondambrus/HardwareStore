import {
  Avatar,
  Box,
  Button,
  IconButton,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Tooltip,
  useTheme,
} from '@mui/material';
import DevicesIcon from '@mui/icons-material/Devices';
import InfoIcon from '@mui/icons-material/Info';
import AddIcon from '@mui/icons-material/Add';
import { Link, useNavigate } from 'react-router-dom';
import { HardwareStoreItem } from '../../models/HardwareStore';
import { useTranslation } from 'react-i18next';

type Props = {
  items: HardwareStoreItem[];
};

const ItemsList: React.FC<Props> = ({ items }) => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const theme = useTheme();
  const handleAdd = () => {
    navigate(`/create`);
  };

  return (
    <Box>
      <Box sx={{ mb: 3, mt: 4 }}>
        <Button variant="outlined" startIcon={<AddIcon />} onClick={handleAdd}>
        {t('hardware_store.add_item')}
        </Button>
      </Box>

      <List>
        {items.map((item, index) => (
          <ListItem key={item.id} sx={{ background: index % 2 === 0 ? theme.palette.action.hover : 'transparent' }}>
            <ListItemAvatar>
              <Avatar>
                <DevicesIcon />
              </Avatar>
            </ListItemAvatar>
            <ListItemText>{item.name}</ListItemText>
            <Tooltip title="Details" placement="top">
              <Link to={`/details/${item.id}`}>
                <IconButton aria-label="details">
                  <InfoIcon />
                </IconButton>
              </Link>
            </Tooltip>
          </ListItem>
        ))}
      </List>
    </Box>
  );
}

export default ItemsList;
