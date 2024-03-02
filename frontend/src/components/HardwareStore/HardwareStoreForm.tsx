import { Box, Button, TextField, Typography } from '@mui/material';
import { FormEvent } from 'react';
import SaveIcon from '@mui/icons-material/Save';
import Joi from 'joi';
import { useTranslation } from 'react-i18next';

export const HardwareStoreSchema = Joi.object({
  name: Joi.string().trim().required(),
  category: Joi.string().trim().required(),
  price: Joi.number().required(),
  quantity: Joi.number().required(),
  description: Joi.string().trim().required(),
});

type HardwareStoreFormProps = {
  title: string;
  name: string;
  setName: (name: string) => void;
  category: string;
  setCategory: (category: string) => void;
  price: string;
  setPrice: (price: string) => void;
  quantity: string;
  setQuantity: (quantity: string) => void;
  description: string;
  setDescription: (description: string) => void;
  onSubmit: (e: FormEvent) => void;
  errors: {
    name?: string;
    category?: string;
    price?: string;
    quantity?: string;
    description?: string;
  };
};

const HardwareStoreForm: React.FC<HardwareStoreFormProps> = ({
  title,
  name,
  setName,
  category,
  setCategory,
  price,
  setPrice,
  quantity,
  setQuantity,
  description,
  setDescription,
  onSubmit,
  errors,
}) => {
  const { t } = useTranslation();
  return (
    <Box>
      <Box textAlign="center" sx={{ mt: 4, mb: 4 }}>
        <Typography variant="h4">{title}</Typography>
      </Box>

      <TextField
        fullWidth
        label= {t('form.name')}
        value={name}
        onChange={(e) => setName(e.target.value)}
        sx={{ mb: 3 }}
        required
        error={!!errors.name}
        helperText={errors.name}
      />

      <TextField
        fullWidth
        label= {t('form.category')}
        value={category}
        onChange={(e) => setCategory(e.target.value)}
        sx={{ mb: 3 }}
        required
        error={!!errors.category}
        helperText={errors.category}
      />

      <TextField
        fullWidth
        label= {t('form.price')}
        value={price}
        type="number"
        InputLabelProps={{
          shrink: true,
        }}
        onChange={(e) => setPrice(e.target.value)}
        sx={{ mb: 3 }}
        required
        error={!!errors.price}
        helperText={errors.price}
      />

      <TextField
        fullWidth
        label= {t('form.quantity')}
        value={quantity}
        type="number"
        InputLabelProps={{
          shrink: true,
        }}
        onChange={(e) => setQuantity(e.target.value)}
        sx={{ mb: 3 }}
        required
        error={!!errors.quantity}
        helperText={errors.quantity}
      />

      <TextField
        fullWidth
        label= {t('form.description')}
        value={description}
        multiline
        rows={2}
        onChange={(e) => setDescription(e.target.value)}
        sx={{ mb: 3 }}
        required
        error={!!errors.description}
        helperText={errors.description}
      />

      <Box sx={{ mb: 3 }}>
        <Button variant="outlined" startIcon={<SaveIcon />} onClick={onSubmit}>
          {t('save')}
        </Button>
      </Box>
    </Box>
  );
};

export default HardwareStoreForm;
