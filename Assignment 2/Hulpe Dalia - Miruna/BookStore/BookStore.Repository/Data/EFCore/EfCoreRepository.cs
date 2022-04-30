using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using BookStore.Repository.Data;

namespace BookStore.Repository.Data.EFCore
{
    public abstract class EfCoreRepository<TEntity, TContext> : IRepository<TEntity>
        where TEntity : class, IEntity
        where TContext : DbContext
    {

        private readonly TContext _context;

        public EfCoreRepository(TContext context)
        {
            _context = context;
        }

        public async Task SaveChangesAsync()
        {
            await _context.SaveChangesAsync();
        }

        public async Task<TEntity> Add(TEntity entity)
        {
            _context.Set<TEntity>().Add(entity);
            await SaveChangesAsync();

            return entity;
        }

        public async Task<TEntity> Delete(Guid id)
        {
            var entity = await _context.Set<TEntity>().FindAsync(id);

            if (entity == null)
            {
                return entity;
            }

            _context.Set<TEntity>().Remove(entity);
            await SaveChangesAsync();

            return entity;
        }

        public async Task<TEntity> Get(Guid id)
        {
            return await _context.Set<TEntity>().FindAsync(id);
        }

        public async Task<IList<TEntity>> GetAll()
        {
            return await _context.Set<TEntity>().ToListAsync();
        }
    }
}
