using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace BookStore.Repository.Data
{
    public interface IRepository<T> where T : class, IEntity
    {
        Task<IList<T>> GetAll();
        Task<T> Get(Guid id);
        Task<T> Add(T entity);
        Task<T> Delete(Guid id);
        Task SaveChangesAsync();
    }
}
