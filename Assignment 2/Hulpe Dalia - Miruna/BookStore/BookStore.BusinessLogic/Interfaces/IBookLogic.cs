using BookStore.BusinessLogic.Models;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading.Tasks;

namespace BookStore.BusinessLogic.Interfaces
{
    public interface IBookLogic
    {
        Task AddBook(BookModel book);
        Task<List<BookModel>> GetAllBooksAync();
        Task DeleteBook(Guid id);
        Task UpdateBook(BookModel book);
        Task<BookModel> GetByTitleAsync(string title);
        Task<List<BookModel>> FindByTitleAsync(string title);
        Task<List<BookModel>> FindByGenreAsync(string genre);
        Task<List<BookModel>> FindByAuthorAsync(string author);
        Task<BookModel> FindByIdAsync(Guid id);
        Task CreateCSVFile();
        Task CreatePdfFile();
        Task CreatePdfFileMethod2();
        Task CreatePdfFileMethod3();
    }
}
