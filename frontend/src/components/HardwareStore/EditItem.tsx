import { FormEvent, useCallback, useEffect, useState } from 'react';
import { CreateHardwareStoreItem } from '../../models/HardwareStore';
import { useNavigate, useParams } from 'react-router-dom';
import { useUpdateHardwareStoreItem } from '../../mutation/hardwarestore.mutation';
import { useHardwareStoreItem } from '../../query/hardwarestore.query';
import useErrors from '../../hooks/useErrors';
import HardwareStoreForm, { HardwareStoreSchema } from './HardwareStoreForm';
import { Container } from '@mui/material';
import State from '../State';
import { useTranslation } from 'react-i18next';
import { useAlert } from '../../hooks/useAlert';

const EditItem: React.FC = () => {
  const { t } = useTranslation();
  const title = t('hardware_store.edit_item_title');
  const { id } = useParams();
  const idNumber = parseInt(id as string);
  const { mutate, isPending, isError, error, isSuccess } = useUpdateHardwareStoreItem(idNumber);
  const {
    data: itemData,
    isLoading: itemIsLoading,
    isError: itemIsError,
    error: itemError,
  } = useHardwareStoreItem(idNumber);
  const alert = useAlert();

  const { errors, setError } = useErrors();
  const [name, setName] = useState('');
  const [description, setDescription] = useState('');
  const [price, setPrice] = useState('');
  const [quantity, setQuantity] = useState('');
  const [category, setCategory] = useState('');

  useEffect(() => {
    if (itemData) {
      setName(itemData.name || '');
      setDescription(itemData.description || '');
      setPrice(itemData.price.toString() || '');
      setQuantity(itemData.quantity.toString() || '');
      setCategory(itemData.category || '');
    }
  }, [itemData]);

  const navigate = useNavigate();

  const handleEdit = useCallback(
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
    if (isSuccess) {
      navigate(`/details/${id}`);
      alert(t('hardware_store.item_updated'), 'success');
    }
  }, [alert, id, isSuccess, navigate, t]);

  return (
    <Container maxWidth="md">
      <State isError={isError || itemIsError} isLoading={isPending || itemIsLoading} error={error || itemError}>
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
          onSubmit={handleEdit}
          errors={errors}
        />
      </State>
    </Container>
  );
};

export default EditItem;
