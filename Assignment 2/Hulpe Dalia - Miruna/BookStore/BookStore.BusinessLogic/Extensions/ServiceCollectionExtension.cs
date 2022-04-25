using AutoMapper;
using BookStore.BusinessLogic.Implementations;
using BookStore.BusinessLogic.Interfaces;
using Microsoft.Extensions.DependencyInjection;

namespace BookStore.Repository.Extensions
{
    public static class ServiceCollectionExtension
    {
        public static void AddBusinessLogic(this IServiceCollection services)
        { 
            services.AddScoped<IBookLogic, BookLogic>();

            services.AddRepository();
        }
    }
}
