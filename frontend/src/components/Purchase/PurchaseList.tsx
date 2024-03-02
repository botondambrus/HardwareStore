import { useNavigate, useParams } from 'react-router-dom';
import State from '../State';
import { Box, Button, IconButton, Tooltip, Typography } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import { DataGrid, GridCellParams, GridValueGetterParams } from '@mui/x-data-grid';
import { useDeletePurchase } from '../../mutation/purchase.mutation';
import { usePurchases } from '../../query/purchase.query';
import AddIcon from '@mui/icons-material/Add';
import { useTranslation } from 'react-i18next';

const PurchaseList: React.FC = () => {
  const { id } = useParams();
  const { t } = useTranslation();
  const navigate = useNavigate();
  const idNumber = parseInt(id as string);
  const { data, isLoading, isError, error } = usePurchases(idNumber);
  const { mutate, isPending, isError: isDeleteError, error: errorDelete } = useDeletePurchase(idNumber);

  const columns = [
    { field: 'name', headerName: t('form.name'), width: 150 },
    { field: 'address', headerName: t('form.address'), width: 200 },
    { field: 'email', headerName: t('form.email'), width: 200 },
    { field: 'quantity', headerName: t('form.quantity'), width: 150 },
    {
      field: 'date',
      headerName: t('form.date'),
      type: 'dateTime',
      width: 300,
      valueGetter: (params: GridValueGetterParams) => {
        return new Date(params.value);
      },
    },

    {
      field: 'actions',
      headerName: t('form.actions'),
      width: 150,
      disableColumnFilter: true,
      disableColumnSort: true,
      renderCell: (params: GridCellParams) => (
        <>
          <Tooltip title={t('purchase.delete_purchase')} placement='top'>
            <IconButton aria-label={t('purchase.delete_purchase')} onClick={() => handleDelete(params.row.id)} color="error">
              <DeleteIcon />
            </IconButton>
          </Tooltip>
          <Tooltip title={t('purchase.edit_purchase')} placement='top'>
            <IconButton aria-label={t('purchase.edit_purchase')} onClick={() => handleEdit(id as string, params.row.id)} color="primary">
              <EditIcon />
            </IconButton>
          </Tooltip>
        </>
      ),
    },
  ];

  const handleDelete = (purchaseId: number) => {
    mutate(purchaseId);
  };

  const handleEdit = (id: string, purchaseId: number) => {
    navigate(`/purchase/${id}/edit/${purchaseId}`);
  };

  const handleAdd = () => {
    navigate(`/purchase/${id}/create`);
  };



  return (
    <State isError={isError || isDeleteError} isLoading={isLoading || isPending} error={error || errorDelete}>
        <Box textAlign="center" sx={{ mt: 4, mb: 3 }}>
          <Typography variant="h4"> {t('purchase.purchase_list')}</Typography>
        </Box>
        <Box sx={{ mb: 3 }}>
          <Button variant="outlined" startIcon={<AddIcon />} onClick={handleAdd}>
           {t('purchase.add_purchase')}
          </Button>
        </Box>
        <DataGrid rows={data ?? []} columns={columns} />
    </State>
  );
};

export default PurchaseList;
