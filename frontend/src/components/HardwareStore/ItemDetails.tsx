import { useNavigate, useParams } from 'react-router-dom';
import { useHardwareStoreItem } from '../../query/hardwarestore.query';
import { Alert, Box, Button, Stack, Typography } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { useDeleteHardwareStoreItem } from '../../mutation/hardwarestore.mutation';
import { useEffect } from 'react';
import State from '../State';
import LocalGroceryStoreIcon from '@mui/icons-material/LocalGroceryStore';
import { useTranslation } from 'react-i18next';
import { useAlert } from '../../hooks/useAlert';

const ItemDetails: React.FC = () => {
  const { id } = useParams();
  const { t } = useTranslation();
  const navigate = useNavigate();
  const idNumber = parseInt(id as string);
  const { data, isLoading, isError, error } = useHardwareStoreItem(idNumber);
  const { mutate, isPending, isError: isDeleteError, error: errorDelete, isSuccess } = useDeleteHardwareStoreItem();
  const showAlert = useAlert();

  useEffect(() => {
    if (isSuccess) {
      showAlert(t('hardware_store.item_deleted'), 'success');
      navigate(`/`);
    }
  }, [id, isSuccess, navigate, showAlert, t]);

  if (!id) {
    return <Alert severity="error"> {t('hardware_store.id_not_found')} </Alert>;
  }

  if (!data && !isLoading) {
    return <Alert severity="error">{t('hardware_store.item_not_found')}</Alert>;
  }

  const handleDelete = () => {
    mutate(idNumber);
    navigate(`/`);
  };

  const handleEdit = () => {
    navigate(`/edit/${id}`);
  };

  const handlePurchase = () => {
    navigate(`/purchase/${id}`);
  };

  return (
    <State isError={isError || isDeleteError} isLoading={isLoading || isPending} error={error || errorDelete}>
      {data && (
        <Box>
          <Box textAlign="center" sx={{ mt: 4, mb: 3 }}>
            <Typography variant="h4">
              {t('hardware_store.item')}: {data.name}
            </Typography>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="h6">{t('form.category')}:</Typography>
            <Typography variant="body1">{data.category}</Typography>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="h6">{t('form.description')}:</Typography>
            <Typography variant="body1">{data.description}</Typography>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="h6">{t('form.price')}:</Typography>
            <Typography variant="body1">{data.price}</Typography>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Typography variant="h6">{t('form.quantity')}:</Typography>
            <Typography variant="body1">{data.quantity}</Typography>
          </Box>

          <Box sx={{ mb: 3 }}>
            <Stack spacing={2} direction="row">
              <Button variant="outlined" startIcon={<DeleteIcon />} color="error" onClick={handleDelete}>
                {t('delete')}
              </Button>
              <Button variant="outlined" startIcon={<EditIcon />} onClick={handleEdit}>
                {t('edit')}
              </Button>
              <Button variant="outlined" color="success" startIcon={<LocalGroceryStoreIcon />} onClick={handlePurchase}>
                {t('purchase.purchase')}
              </Button>
            </Stack>
          </Box>
        </Box>
      )}
    </State>
  );
};

export default ItemDetails;
