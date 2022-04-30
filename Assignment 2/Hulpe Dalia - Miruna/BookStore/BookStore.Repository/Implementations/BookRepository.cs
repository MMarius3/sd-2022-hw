using BookStore.Repository.Data;
using BookStore.Repository.Data.EFCore;
using BookStore.Repository.Interfaces;
using BookStore.Repository.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BookStore.Repository.Implementations
{
    public class BookRepository : EfCoreRepository<BookEntity, ApplicationDbContext>, IBookRepository
    {
        private readonly DbSet<BookEntity> _dbSet;
        private readonly ApplicationDbContext _context;

        public BookRepository(ApplicationDbContext context) : base(context)
        {
            _dbSet = context.Set<BookEntity>();
            _context = context;
        }

        public async Task<List<BookEntity>> FindByTitleAsync (string title)
        {
            var book = await _dbSet.Where(b => b.Title.Equals(title)).ToListAsync();

            return book;
        }

        public async Task<BookEntity> FindByIdAsync(Guid id)
        {
            var book = await _dbSet.Where(b => b.Id.Equals(id)).FirstOrDefaultAsync();

            return book;
        }

        public async Task<List<BookEntity>> FindByGenreAsync(string genre)
        {
            var book = await _dbSet.Where(b => b.Genre.Equals(genre)).ToListAsync();

            return book;
        }

        public async Task<List<BookEntity>> FindByAuthorAsync(string author)
        {
            var book = await _dbSet.Where(b => b.Author.Equals(author)).ToListAsync();

            return book;
        }

        public async Task<List<BookEntity>> GetSoldOutBooksAsync()
        {
            var books = await _dbSet.Where(b => b.Quantity == 0).ToListAsync();

            return books;
        }

        public async Task<BookEntity> Update(BookEntity entity)
        {
            var local = _dbSet
                .Local
                .FirstOrDefault(b => b.Id.Equals(entity.Id));

            if (local != null)
            {
                _context.Entry(local).State = EntityState.Detached;
            }

            _context.Entry(entity).State = EntityState.Modified;

            _context.SaveChanges();

            return entity;
        }
    }
}
