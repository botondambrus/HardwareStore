import { Container } from '@mui/material';
import { useCreateHardwareStoreItem } from '../../mutation/hardwarestore.mutation';
import { FormEvent, useCallback, useEffect, useState } from 'react';
import { CreateHardwareStoreItem } from '../../models/HardwareStore';
import { useNavigate } from 'react-router-dom';
import useErrors from '../../hooks/useErrors';
import HardwareStoreForm, { HardwareStoreSchema } from './HardwareStoreForm';
import State from '../State';
import { useTranslation } from 'react-i18next';
import { useAlert } from '../../hooks/useAlert';

const CreateItem: React.FC = () => {
  const { t } = useTranslation();
  const title = t('hardware_store.create_item_title');
  const { mutate, data, isPending, isError, error, isSuccess } = useCreateHardwareStoreItem();

  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [category, setCategory] = useState('');
  const { errors, setError } = useErrors();

  const navigate = useNavigate();
  const alert = useAlert();

  const handleCreate = useCallback(
    (e: FormEvent) => {
      e.preventDefault();

      const hardwareStoreItem: CreateHardwareStoreItem = {
        name: name,
        description: description,
        price: parseFloat(price),
        quantity: parseFloat(quantity),
        category: category,
      };

      const { error } = HardwareStoreSchema.validate(hardwareStoreItem);

      if (error) {
        error.details.forEach((detail) => {
          const fieldName = typeof detail.path[0] === 'number' ? detail.path[0].toString() : detail.path[0];
          setError(fieldName, detail.message);
        });
      } else {
        mutate(hardwareStoreItem);
      }
    },
    [name, description, price, quantity, category, setError, mutate],
  );

  useEffect(() => {
    if (isSuccess && data) {
      navigate(`/details/${data.id}`);
      alert(t('hardware_store.item_created'), 'success');
    }
  }, [isSuccess, data, navigate, alert, t]);

  return (
    <Container maxWidth="md">
      <State isError={isError} isLoading={isPending} error={error}>
        <HardwareStoreForm
          title={title}
          name={name}
          setName={setName}
          category={category}
          setCategory={setCategory}
          price={price}
          setPrice={setPrice}
          quantity={quantity}
          setQuantity={setQuantity}
          description={description}
          setDescription={setDescription}
          onSubmit={handleCreate}
          errors={errors}
        />
      </State>
    </Container>
  );
};

export default CreateItem;
