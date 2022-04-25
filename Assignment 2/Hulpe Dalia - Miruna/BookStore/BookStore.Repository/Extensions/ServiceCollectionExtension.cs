using BookStore.Repository.Implementations;
using BookStore.Repository.Interfaces;
using Microsoft.Extensions.DependencyInjection;
namespace BookStore.Repository.Extensions
{
    public static class ServiceCollectionExtension
    {
        public static void AddRepository(this IServiceCollection services)
        {
            services.AddScoped<IBookRepository, BookRepository>();
        }
    }
}
