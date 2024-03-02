import { useHardwareStoreItems } from '../../query/hardwarestore.query';
import ItemsList from './ItemsList';
import State from '../State';
import { useState } from 'react';
import { Box, Button, Collapse, Divider, Grid, TextField, Typography } from '@mui/material';
import { HardwareStoreFilter } from '../../models/HardwareStoreFilter';
import { useTranslation } from 'react-i18next';
import { GridSearchIcon } from '@mui/x-data-grid';
import FilterAltIcon from '@mui/icons-material/FilterAlt';

const HardwareStore: React.FC = () => {
  const { t } = useTranslation();
  const [filter, setFilter] = useState<HardwareStoreFilter>({});
  const [isOpen, setIsOpen] = useState(false);

  const { data, isLoading, isError, error, refetch } = useHardwareStoreItems(filter);

  const handleToggle = () => {
    setIsOpen(!isOpen);
  };

  const handleApply = () => {
    refetch();
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setFilter(prevFilter => {
      if (value === '') {
        const { ...updatedFilter } = prevFilter;
        delete updatedFilter[name];
        return updatedFilter;
      }
      return {
        ...prevFilter,
        [name]: value,
      };
    });
  };

  return (
    <State isError={isError} isLoading={isLoading} error={error}>
      <Box textAlign="center" sx={{ mt: 4, mb: 3 }}>
        <Typography variant="h5" component="div">
          {t('hardware_store.item_list_title')}
        </Typography>
      </Box>
      <Box sx={{ mt: 4 }}>
        <Button variant="outlined" startIcon={<FilterAltIcon />} onClick={handleToggle}>
          {isOpen ? t('search.hide_filters') : t('search.show_filters')}
        </Button>
      </Box>

      <Collapse in={isOpen}>
        <Box>
          <Box sx={{ mt: 4, mb: 3 }}>
            <Typography variant="h6">{t('search.filters')}</Typography>
          </Box>
          <Grid container spacing={4} justifyContent="center">
          <Grid item xs={12} sm={3}>
              <TextField label={t('search.name')} name="name" value={filter.name} onChange={handleChange} fullWidth />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField label={t('search.category')} name="category" value={filter.category} onChange={handleChange} fullWidth />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField
                label={t('search.min_price')}
                name="minPrice"
                type="number"
                value={filter.minPrice}
                onChange={handleChange}
                fullWidth
              />
            </Grid>
            <Grid item xs={12} sm={3}>
              <TextField
                label={t('search.max_price')}
                name="maxPrice"
                type="number"
                value={filter.maxPrice}
                onChange={handleChange}
                fullWidth
              />
            </Grid>
          </Grid>
          <Box sx={{ mt: 4, mb: 3 }}>
            <Button variant="contained" startIcon={<GridSearchIcon />} onClick={handleApply}>
              {t('search.search')}
            </Button>
          </Box>
          <Box sx={{ mt: 4 }}>
            <Divider />
          </Box>
        </Box>
      </Collapse>
      <ItemsList items={data ?? []} />
    </State>
  );
};

export default HardwareStore;
