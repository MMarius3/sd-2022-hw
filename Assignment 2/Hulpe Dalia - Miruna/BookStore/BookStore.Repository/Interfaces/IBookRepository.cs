using BookStore.Repository.Data;
using BookStore.Repository.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace BookStore.Repository.Interfaces
{
    public interface IBookRepository : IRepository<BookEntity>
    {
        Task<List<BookEntity>> FindByTitleAsync(string title);
        Task<List<BookEntity>> FindByGenreAsync(string genre);
        Task<List<BookEntity>> FindByAuthorAsync(string author);
        Task<BookEntity> Update(BookEntity entity);
        Task<BookEntity> FindByIdAsync(Guid id);
        Task<List<BookEntity>> GetSoldOutBooksAsync();
    }
}
